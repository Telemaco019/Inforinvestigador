package com.unibs.zanotti.inforinvestigador.data.remote;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import com.unibs.zanotti.inforinvestigador.domain.utils.DateUtils;
import io.reactivex.Single;

import java.util.Optional;

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
    public Single<Optional<User>> getUser(String userId) {
        return Single.just(Optional.empty());
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public Single<Optional<User>> getCurrentUser() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        Optional<User> optionalUser = Optional.empty();
        if (firebaseUser != null) {
            optionalUser = Optional.of(new User(
                    firebaseUser.getUid(),
                    firebaseUser.getEmail(),
                    firebaseUser.getDisplayName(),
                    firebaseUser.getPhotoUrl(),
                    DateUtils.fromInstantTimestamp(firebaseUser.getMetadata().getCreationTimestamp())));
        }
        return Single.just(optionalUser);
    }
}
