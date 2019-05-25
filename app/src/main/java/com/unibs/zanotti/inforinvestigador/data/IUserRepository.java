package com.unibs.zanotti.inforinvestigador.data;


import android.net.Uri;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

public interface IUserRepository {

    Maybe<User> getUser(String userId);

    Completable saveUser(User user);

    /**
     * Return the id of the user currently logged in Invorinvestigador
     */
    String getCurrentUserId();

    /**
     * Update the profile picture of the user provided as argument
     *
     * @param userId
     * @param imageUri URI of the image to set as profile picture
     * @return An observable emitting the progress of the profile picture updating (which ranges from 0 to 100)
     */
    Observable<Double> updateUserProfilePicture(String userId, Uri imageUri);

    Completable updateUserField(String userId, String fieldName, Object newValue);
}
