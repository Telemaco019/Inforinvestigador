package com.unibs.zanotti.inforinvestigador.profile;

import android.net.Uri;
import com.unibs.zanotti.inforinvestigador.baseMVP.BaseContract;

public interface ProfileContract {
    interface Presenter extends BaseContract.Presenter<View> {

    }

    interface View extends BaseContract.View {

        void showProfilePicture(Uri profilePictureUri);

        void showUserName(String name);

        void showUserEmail(String email);

        void showSettingsIcon();

        void showEditProfileButton();

        void showFollowButton();
    }
}
