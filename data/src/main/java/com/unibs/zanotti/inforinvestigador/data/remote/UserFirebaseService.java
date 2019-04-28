package com.unibs.zanotti.inforinvestigador.data.remote;

import com.google.firebase.firestore.FirebaseFirestore;
import com.unibs.zanotti.inforinvestigador.domain.IUserService;
import com.unibs.zanotti.inforinvestigador.domain.model.User;

import java.util.Optional;

public class UserFirebaseService implements IUserService {
    private static final String TAG = String.valueOf(UserFirebaseService.class);

    private FirebaseFirestore db;

    public UserFirebaseService() {
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public Optional<User> getUser(Long userId) {
        return Optional.empty();
    }

    @Override
    public void updateUser(User user) {

    }
}
