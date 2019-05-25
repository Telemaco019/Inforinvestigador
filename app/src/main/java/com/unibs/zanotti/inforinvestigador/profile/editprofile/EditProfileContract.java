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
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void onProfilePictureEdited(int resultCode, Intent data);
    }
}
