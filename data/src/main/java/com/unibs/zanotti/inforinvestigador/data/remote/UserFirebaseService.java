package com.unibs.zanotti.inforinvestigador.data.remote;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unibs.zanotti.inforinvestigador.domain.IUserService;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import com.unibs.zanotti.inforinvestigador.domain.utils.DateUtils;

import java.util.Optional;

public class UserFirebaseService implements IUserService {
    private static final String TAG = String.valueOf(UserFirebaseService.class);
    private static UserFirebaseService INSTANCE = null;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private UserFirebaseService(FirebaseFirestore firestoreDb, FirebaseAuth firebaseAuth) {
        this.db = firestoreDb;
        this.mAuth = firebaseAuth;
    }

    public static UserFirebaseService getInstance(FirebaseFirestore firestoreDb, FirebaseAuth firebaseAuth) {
        if (INSTANCE == null) {
            INSTANCE = new UserFirebaseService(firestoreDb, firebaseAuth);
        }
        return INSTANCE;
    }

    @Override
    public Optional<User> getUser(Long userId) {
        return Optional.empty();
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public User getCurrentUser(User user) {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            return new User(
                    firebaseUser.getUid(),
                    firebaseUser.getEmail(),
                    firebaseUser.getDisplayName(),
                    firebaseUser.getPhotoUrl(),
                    DateUtils.fromInstantTimestamp(firebaseUser.getMetadata().getCreationTimestamp()));
        }
        return null;
    }
}
