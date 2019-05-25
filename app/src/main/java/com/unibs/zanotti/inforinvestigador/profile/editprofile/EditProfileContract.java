package com.unibs.zanotti.inforinvestigador.profile.editprofile;

import android.content.Intent;
import android.net.Uri;
import com.unibs.zanotti.inforinvestigador.baseMVP.BaseContract;

public interface EditProfileContract {
    interface View extends BaseContract.View {

        void showEditProfilePictureErrorMessage();

        void updateProgressBar(Double progress);

        void showProfilePicture(Uri imageUri);

        void hideProgressBarUploadProfilePicture();

        void showProgressBarUploadProfilePicture();

        void setActivityResult(int result);

        void showUserPhone(String phone);

        void showUserLocation(String location);

        void showUserName(String name);

        void finishActivity();

        /**
         * Show the user a message communicating that he has to wait for the profile picture to be saved
         */
        void showMessageWaitProfilePictureSaving();

        void showProgressSavingUserProfileFields();

        void hideProgressBarSavingUserProfileFields();
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void onProfilePictureEdited(int resultCode, Intent data);

        void updateUserProfileFields(String name, String phone, String location);
    }
}
