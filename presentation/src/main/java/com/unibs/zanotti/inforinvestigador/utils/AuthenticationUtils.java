package com.unibs.zanotti.inforinvestigador.utils;

import android.net.Uri;
import com.google.firebase.auth.FirebaseUser;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import com.unibs.zanotti.inforinvestigador.domain.utils.DateUtils;

import java.time.LocalDateTime;

public class AuthenticationUtils {
    /**
     * Given as input argument a firebase user, return the corresponding {@link User user model} of Inforinvestigador
     *
     * @param firebaseUser
     * @return
     */
    public static User fromFirebaseToInforinvestigador(FirebaseUser firebaseUser) {
        String userEmail = firebaseUser.getEmail();
        String userId = firebaseUser.getUid();
        String userName = firebaseUser.getDisplayName();
        Uri userProfilePicUri = firebaseUser.getPhotoUrl();
        LocalDateTime creationDateTime = DateUtils.fromInstantTimestamp(firebaseUser.getMetadata().getCreationTimestamp());

        return new User(
                userId,
                userEmail,
                userName,
                userProfilePicUri,
                creationDateTime
        );
    }
}
