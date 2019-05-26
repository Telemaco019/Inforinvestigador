package com.unibs.zanotti.inforinvestigador.profile;

import android.net.Uri;
import com.unibs.zanotti.inforinvestigador.baseMVP.BaseContract;
import com.unibs.zanotti.inforinvestigador.domain.model.User;

public interface ProfileContract {
    interface Presenter extends BaseContract.Presenter<View> {

        void editProfile();

        /**
         * Called when the user profile has been edited
         */
        void onProfileEdited();

        void onFollowButtonClicked();

        void onUnfollowButtonClicked();
    }

    interface View extends BaseContract.View {

        void showProfilePicture(Uri profilePictureUri);

        void showUserName(String name);

        void showUserEmail(String email);

        void showSettingsIcon();

        void showEditProfileButton();

        void showFollowButton();

        void startEditProfileActivity(User user);

        void showUserLocation(String location);

        void showUserPhone(String phone);

        void hideUserLocationField();

        void hideUserPhoneField();

        void showUserSharesNumber(int sharesNumber);

        void showUserFollowersNumber(int followersNumber);

        void showUserFollowingNumber(int followingNumber);

        void showEmptyUserProfile();

        void showUnfollowButton();

        void hideFollowButton();

        void hideUnfollowButton();
    }
}
