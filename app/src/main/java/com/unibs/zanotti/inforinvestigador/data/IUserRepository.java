package com.unibs.zanotti.inforinvestigador.data;


import android.net.Uri;
import com.unibs.zanotti.inforinvestigador.domain.model.ResearcherSuggestion;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

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
     * @param imageUri URI of the imageUri to set as profile picture
     * @return An observable emitting the progress of the profile picture updating (which ranges from 0 to 100)
     */
    Observable<Double> updateUserProfilePicture(String userId, Uri imageUri);

    Completable updateUserField(String userId, String fieldName, Object newValue);

    /**
     * Return an observable emitting the researcher suggestions for the user with the id provided as argument
     *
     * @param userId
     * @return
     */
    Observable<ResearcherSuggestion> getResearchersSuggestions(String userId);

    /**
     * Return a Single emitting a true value if the user associated to the first ID provided as argument is following
     * the user associated to the id provided as second argument
     *
     * @param follower
     * @param followed
     * @return
     */
    Single<Boolean> isFollowing(String follower, String followed);

    Completable followUser(String follower, String followed);

    Completable unfollowUser(String follower, String followed);

    /**
     * Return the list of the ids of the users followed by the user identified by the ID provided as argument
     *
     * @param userId
     * @return
     */
    Observable<String> getFollowingUsersIds(String userId);

    /**
     * Return the list of the ids of the users following the user identified by the ID provided as argument
     *
     * @param userId
     * @return
     */
    Observable<String> getFollowersUsersIds(String userId);

    Completable saveFirebaseToken(String userId, String token);
}
