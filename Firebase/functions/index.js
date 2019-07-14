const functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp()

exports.sendCommentNotification = functions.firestore.document('/papers/{paperId}/comments/{commentId}').onCreate((snap, context) => {
    const comment = snap.data();
    const commentAuthorId = comment.authorId;
    const sharedPaperId = context.params.paperId;

    const getSharedPaperPromise = admin.firestore().doc(`/papers/${sharedPaperId}`).get();

    return getSharedPaperPromise.then(result => {
        return result.data().sharingUserId;
    }).then(sharingUserId => {
        return admin.firestore().doc(`/users/${sharingUserId}`).get();
    }).then(docSnapshot => {
        if (docSnapshot.exists && commentAuthorId != docSnapshot.id) {
            const sharingUser = docSnapshot.data();
            const deviceInstanceId = sharingUser.instanceId;
            const notificationPayload = {
                data: {
                    notificationType: 'comment',
                    commentAuthorName: comment.authorName,
                    commentAuthorId: commentAuthorId,
                    body: comment.body,
                    commentedPaperId: sharedPaperId,
                }
            }
            console.log('Sending notification to device with instance id ' + deviceInstanceId);
            console.log('Notified user id: ' + sharingUser.id + ' | Comment author id: ' + commentAuthorId);
            admin.messaging().sendToDevice(deviceInstanceId, notificationPayload)
                .then(response => console.log('Message sent successfully. ', response))
                .catch(error => console.log('Error sending message. ', error));
        }
    });
    /* const message = event.data.current.val();
        const senderUid = message.from;
        const receiverUid = message.to;
        const promises = [];

        if (senderUid == receiverUid) {
            //if sender is receiver, don't send notification
            promises.push(event.data.current.ref.remove());
            return Promise.all(promises);
        }

        const getInstanceIdPromise = admin.database().ref(`/users/${receiverUid}/instanceId`).once('value');
        const getReceiverUidPromise = admin.auth().getUser(receiverUid);

        return Promise.all([getInstanceIdPromise, getReceiverUidPromise]).then(results => {
            const instanceId = results[0].val();
            const receiver = results[1];
            console.log('notifying ' + receiverUid + ' about ' + message.body + ' from ' + senderUid);

            const payload = {
                notification: {
                    title: receiver.displayName,
                    body: message.body,
                    icon: receiver.photoURL
                }
            };

            admin.messaging().sendToDevice(instanceId, payload)
                .then(function (response) {
                    console.log("Successfully sent message:", response);
                })
                .catch(function (error) {
                    console.log("Error sending message:", error);
                });
        });*/
});