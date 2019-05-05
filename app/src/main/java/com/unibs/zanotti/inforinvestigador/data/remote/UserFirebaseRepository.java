package com.unibs.zanotti.inforinvestigador.data.remote;

import android.net.Uri;
import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.data.remote.model.Collections;
import com.unibs.zanotti.inforinvestigador.data.remote.model.UserEntity;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import com.unibs.zanotti.inforinvestigador.domain.utils.DateUtils;
import com.unibs.zanotti.inforinvestigador.domain.utils.StringUtils;
import com.unibs.zanotti.inforinvestigador.utils.FirebaseUtils;
import io.reactivex.Completable;
import io.reactivex.Maybe;

import java.time.LocalDateTime;

public class UserFirebaseRepository implements IUserRepository {
    private static final String TAG = String.valueOf(UserFirebaseRepository.class);
    private static UserFirebaseRepository INSTANCE = null;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private UserFirebaseRepository(FirebaseFirestore firestoreDb, FirebaseAuth firebaseAuth) {
        this.db = firestoreDb;
        this.mAuth = firebaseAuth;
    }

    public static UserFirebaseRepository getInstance(FirebaseFirestore firestoreDb, FirebaseAuth firebaseAuth) {
        if (INSTANCE == null) {
            INSTANCE = new UserFirebaseRepository(firestoreDb, firebaseAuth);
        }
        return INSTANCE;
    }

    @Override
    public Maybe<User> getUser(String userId) {
        return Maybe.create(emitter -> {
            // TODO
            emitter.onSuccess(new User("id", "asds@gmail.com", "pippo baudo", Uri.EMPTY, LocalDateTime.now()));
        });
    }

    @Override
    public Completable saveUpdateUser(User user) {
        return Completable.create(emitter -> db.document(String.format("%s/%s", Collections.USERS, user.getId()))
                .set(fromUser(user))
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, String.format(FirebaseUtils.LOG_MSG_STANDARD_WRITE_SUCCESS, "user", user.getId()));
                    emitter.onComplete();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, String.format(FirebaseUtils.LOG_MSG_STANDARD_SAVE_ERROR, "user", user.getId(), e));
                    emitter.onError(e);
                }));
    }

    @Override
    public String getCurrentUserId() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return currentUser == null ? null : currentUser.getUid();
    }

    private UserEntity fromUser(User user) {
        return new UserEntity(user.getId(),
                user.getEmail(),
                user.getName(),
                user.getProfilePictureUri() == null ? StringUtils.BLANK : user.getProfilePictureUri().toString(),
                DateUtils.fromLocalDateTimeToEpochMills(user.getCreationDateTime()));
    }
}
