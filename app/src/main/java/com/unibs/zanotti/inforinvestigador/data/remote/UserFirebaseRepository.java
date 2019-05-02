package com.unibs.zanotti.inforinvestigador.data.remote;

import android.net.Uri;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import com.unibs.zanotti.inforinvestigador.domain.utils.DateUtils;
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
    public void updateUser(User user) {

    }

    @Override
    public Maybe<User> getCurrentUser() {
        return Maybe.create(emitter -> {
            FirebaseUser firebaseUser = mAuth.getCurrentUser();
            if (firebaseUser != null) {
                emitter.onSuccess(new User(
                        firebaseUser.getUid(),
                        firebaseUser.getEmail(),
                        firebaseUser.getDisplayName(),
                        firebaseUser.getPhotoUrl(),
                        DateUtils.fromEpochTimestampSec(firebaseUser.getMetadata().getCreationTimestamp())));
            }
            emitter.onComplete();
        });
    }
}
