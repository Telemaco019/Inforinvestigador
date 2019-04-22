package com.unibs.zanotti.inforinvestigador.auth;

import com.google.firebase.auth.FirebaseUser;
import com.unibs.zanotti.inforinvestigador.BasePresenter;
import com.unibs.zanotti.inforinvestigador.BaseView;

public interface LoginContract {
    interface Presenter extends BasePresenter {
        void performLogin(String email, String password);
    }

    interface View extends BaseView<Presenter> {
        void showErrorEmailFieldEmpty();

        void showErrorPasswordFieldEmpty();

        void showAuthenticationFailed();

        void showPasswordForgotLink();

        void updateUI(FirebaseUser user);
    }
}
