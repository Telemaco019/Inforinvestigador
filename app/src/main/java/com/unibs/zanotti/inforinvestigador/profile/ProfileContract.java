package com.unibs.zanotti.inforinvestigador.profile;

import android.net.Uri;
import com.unibs.zanotti.inforinvestigador.BasePresenter;
import com.unibs.zanotti.inforinvestigador.BaseView;

public interface ProfileContract {
    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<Presenter> {

        void showProfilePicture(Uri profilePictureUri);

        void showUserName(String name);

        void showUserEmail(String email);
    }
}
