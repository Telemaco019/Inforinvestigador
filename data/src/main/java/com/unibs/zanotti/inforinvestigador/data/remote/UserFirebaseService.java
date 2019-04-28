package com.unibs.zanotti.inforinvestigador.data.remote;

import android.util.Log;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unibs.zanotti.inforinvestigador.data.remote.model.Collections;
import com.unibs.zanotti.inforinvestigador.data.remote.model.UserEntity;
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
    public void saveUser(User user) {
        UserEntity userEntity = new UserEntity(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getProfilePictureUri() == null ? null : user.getProfilePictureUri().toString(),
                user.getCreationDateTime(),
                user.isVerified()
        );

        db.collection(Collections.USERS)
                .document(userEntity.getId())
                .set(userEntity)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "added document user with id " + user.getId()))
                .addOnFailureListener(e -> Log.d(TAG, "failed to add document user: " + e));
    }
}
