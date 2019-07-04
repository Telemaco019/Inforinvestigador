const functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp()

exports.sendNotification = functions.firestore.document('/papers/{paperId}/comments/{commentId}').onCreate((snap, context) => {
    const promises = [];
    const comment = snap.data();
    const commentAuthorId = comment.commentAuthorId;
    const sharedPaperId = context.params.paperId;

    const getSharedPaperPromise = admin.firestore().doc(`/papers/${sharedPaperId}`).get();
    
    return getSharedPaperPromise.then(result => {
        return result.data().sharingUserId;
    }).then(sharingUserId => {
        return admin.firestore().doc(`/users/${sharingUserId}`).get();
    }).then(sharingUser => {
        if(commentAuthorId != sharingUser.id) {
            console.log('Notifying to user ' + sharingUser.id + ' new comment on paper ' + sharedPaperId + ' made by user ' + commentAuthorId);
            const userInstanceId = sharingUser.instanceId;
            const notificationPayload = {
                notification: {
                    title: sharingUser.name,
                    body: comment.body,
                }
            }
            admin.messaging().sendToDevice(userInstanceId,notificationPayload)
            .then(response => console.log('Message sent successfully. ',response))
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