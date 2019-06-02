package com.unibs.zanotti.inforinvestigador.data.remote;

import android.net.Uri;
import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.data.remote.model.Collections;
import com.unibs.zanotti.inforinvestigador.data.remote.model.UserEntity;
import com.unibs.zanotti.inforinvestigador.domain.model.ResearcherSuggestion;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import com.unibs.zanotti.inforinvestigador.domain.utils.DateUtils;
import com.unibs.zanotti.inforinvestigador.domain.utils.StringUtils;
import com.unibs.zanotti.inforinvestigador.utils.FirebaseUtils;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserFirebaseRepository implements IUserRepository {
    private static final String TAG = String.valueOf(UserFirebaseRepository.class);
    private static UserFirebaseRepository INSTANCE = null;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;

    private UserFirebaseRepository(FirebaseFirestore firestoreDb, FirebaseAuth firebaseAuth, FirebaseStorage firebaseStorage) {
        this.firebaseFirestore = firestoreDb;
        this.firebaseAuth = firebaseAuth;
        this.firebaseStorage = firebaseStorage;
    }

    public static UserFirebaseRepository getInstance(FirebaseFirestore firestoreDb, FirebaseAuth firebaseAuth, FirebaseStorage firebaseStorage) {
        if (INSTANCE == null) {
            INSTANCE = new UserFirebaseRepository(firestoreDb, firebaseAuth, firebaseStorage);
        }
        return INSTANCE;
    }

    @Override
    public Maybe<User> getUser(String userId) {
        return Maybe.create(emitter -> firebaseFirestore.document(String.format("%s/%s", Collections.USERS, userId))
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    Log.d(TAG, String.format(FirebaseUtils.LOG_MSG_STANDARD_SINGLE_READ_SUCCESS, "user", userId));
                    emitter.onSuccess(fromEntity(Objects.requireNonNull(documentSnapshot.toObject(UserEntity.class))));
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, String.format(FirebaseUtils.LOG_MSG_STANDARD_READ_ERROR, "user", e.toString()));
                })
                .addOnCompleteListener(e -> emitter.onComplete()));
    }

    @Override
    public Completable saveUser(User user) {
        return Completable.create(emitter -> firebaseFirestore.document(String.format("%s/%s", Collections.USERS, user.getId()))
                .set(fromUser(user))
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, String.format(FirebaseUtils.LOG_MSG_STANDARD_WRITE_SUCCESS, "user", user.getId()));
                    emitter.onComplete();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, String.format(FirebaseUtils.LOG_MSG_STANDARD_SAVE_ERROR, "user", user.getId()), e);
                    emitter.onError(e);
                }));
    }

    @Override
    public String getCurrentUserId() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        return currentUser == null ? null : currentUser.getUid();
    }

    @Override
    public Observable<Double> updateUserProfilePicture(String userId, Uri imageUri) {
        return Observable.create(emitter -> {
            StorageReference storageReference = firebaseStorage.getReference();
            String imagePath = String.format("%s/%s%s", FirebaseUtils.STORAGE_REFERENCE_PATH_PROFILE_PICTURES,
                    userId,
                    FirebaseUtils.PROFILE_PICTURES_EXTENSION);
            StorageReference imageReference = storageReference.child(imagePath);
            UploadTask uploadTask = imageReference.putFile(imageUri);

            uploadTask.addOnProgressListener(taskSnapshot -> {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                emitter.onNext(progress);
            }).continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return imageReference.getDownloadUrl();
            }).continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                Uri url = task.getResult();
                return firebaseFirestore.document(String.format("%s/%s", Collections.USERS, userId))
                        .update(FirebaseUtils.FIRESTORE_DOCUMENT_USER_FIELD_PROFILE_PICTURE_URI, url.toString());
            }).addOnSuccessListener(taskSnapshot -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError)
                    .addOnCompleteListener(task -> emitter.onComplete());
        });
    }

    @Override
    public Completable updateUserField(String userId, String fieldName, Object newValue) {
        return Completable.create(emitter -> {
            String userPath = String.format("%s/%s", Collections.USERS, userId);
            firebaseFirestore.document(userPath)
                    .update(fieldName, newValue)
                    .addOnSuccessListener(aVoid -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        });
    }

    @Override
    public Observable<ResearcherSuggestion> getResearchersSuggestions(String userId) {
        // Dummy implementation: as suggestions return all the researchers except the one with the id provided as argument
        return Observable.create(emitter -> {
            firebaseFirestore.collection(Collections.USERS)
                    .get()
                    .addOnSuccessListener(snapshots -> {
                        snapshots.getDocuments()
                                .stream()
                                .filter(doc -> !doc.get(FirebaseUtils.FIRESTORE_DOCUMENT_USER_FIELD_ID).equals(userId))
                                .map(doc -> doc.toObject(UserEntity.class))
                                .map(userEntity -> new ResearcherSuggestion(userEntity.getId(),
                                        Uri.parse(userEntity.getProfilePictureUri()),
                                        userEntity.getName()))
                                .forEach(emitter::onNext);
                    })
                    .addOnFailureListener(emitter::onError)
                    .addOnCompleteListener(snapshotTask -> emitter.onComplete());
        });
    }

    @Override
    public Single<Boolean> isFollowing(String follower, String followed) {
        return Single.create(emitter -> {
            String firestoreDocPath = String.format("%s/%s/%s/%s", Collections.USERS, followed, Collections.FOLLOWERS, follower);
            firebaseFirestore.document(firestoreDocPath)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> emitter.onSuccess(documentSnapshot.exists()))
                    .addOnFailureListener(emitter::onError);
        });
    }

    @Override
    public Completable followUser(String follower, String followed) {
        return Completable.create(emitter -> {
            Map<String, Boolean> dummyData = new HashMap<>();
            dummyData.put("exists", Boolean.TRUE);
            String firestoreFollowerDocPath = String.format("%s/%s/%s/%s", Collections.USERS, followed, Collections.FOLLOWERS, follower);
            String firestoreFollowingDocPath = String.format("%s/%s/%s/%s", Collections.USERS, follower, Collections.FOLLOWING, followed);
            String firestoreFollowedUserDocPath = String.format("%s/%s", Collections.USERS, followed);
            String firestoreFollowerUserDocPath = String.format("%s/%s", Collections.USERS, follower);

            // TODO: replace increment with cloud function (more robust)
            firebaseFirestore.document(firestoreFollowerDocPath)
                    .set(dummyData)
                    .continueWithTask(task -> firebaseFirestore.document(firestoreFollowingDocPath).set(dummyData))
                    .continueWithTask(task -> firebaseFirestore.document(firestoreFollowedUserDocPath).update("followersNumber", FieldValue.increment(1L)))
                    .continueWithTask(task -> firebaseFirestore.document(firestoreFollowerUserDocPath).update("followingNumber", FieldValue.increment(1L)))
                    .addOnSuccessListener(aVoid -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        });
    }

    @Override
    public Completable unfollowUser(String follower, String followed) {
        return Completable.create(emitter -> {
            Map<String, Boolean> dummyData = new HashMap<>();
            dummyData.put("exists", Boolean.TRUE);
            String firestoreFollowerDocPath = String.format("%s/%s/%s/%s", Collections.USERS, followed, Collections.FOLLOWERS, follower);
            String firestoreFollowingDocPath = String.format("%s/%s/%s/%s", Collections.USERS, follower, Collections.FOLLOWING, followed);
            String firestoreFollowedUserDocPath = String.format("%s/%s", Collections.USERS, followed);
            String firestoreFollowerUserDocPath = String.format("%s/%s", Collections.USERS, follower);

            // TODO: replace increment with cloud function (more robust)
            firebaseFirestore.document(firestoreFollowerDocPath)
                    .delete()
                    .continueWithTask(task -> firebaseFirestore.document(firestoreFollowingDocPath).delete())
                    .continueWithTask(task -> firebaseFirestore.document(firestoreFollowedUserDocPath).update("followersNumber", FieldValue.increment(-1L)))
                    .continueWithTask(task -> firebaseFirestore.document(firestoreFollowerUserDocPath).update("followingNumber", FieldValue.increment(-1L)))
                    .addOnSuccessListener(aVoid -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        });
    }

    @Override
    public Observable<User> getFollowingUsers(String userId) {
        return Observable.create(emitter -> {
//           String firestoreFollowingCollectionPath = String.format("%s/%s/")
        });
    }

    private UserEntity fromUser(User user) {
        return new UserEntity(user.getId(),
                user.getEmail(),
                user.getName(),
                user.getPhone(),
                user.getLocation(),
                user.getSharesNumber(),
                user.getFollowingNumber(),
                user.getFollowersNumber(),
                user.getProfilePictureUri() == null ? StringUtils.BLANK : user.getProfilePictureUri().toString(),
                DateUtils.fromLocalDateTimeToEpochMills(user.getCreationDateTime()));
    }

    private User fromEntity(UserEntity userEntity) {
        return new User(userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getName(),
                userEntity.getPhone(),
                userEntity.getLocation(),
                userEntity.getSharesNumber(),
                userEntity.getFollowingNumber(),
                userEntity.getFollowersNumber(),
                Uri.parse(userEntity.getProfilePictureUri()),
                DateUtils.fromEpochTimestampMillis(userEntity.getCreationEpochTimestampMillis()));
    }
}
