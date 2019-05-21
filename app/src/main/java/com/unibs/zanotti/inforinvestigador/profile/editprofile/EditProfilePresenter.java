package com.unibs.zanotti.inforinvestigador.profile.editprofile;

import com.unibs.zanotti.inforinvestigador.baseMVP.BasePresenter;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;

public class EditProfilePresenter extends BasePresenter<EditProfileContract.View> implements EditProfileContract.Presenter {
    private IUserRepository userRepository;

    public EditProfilePresenter(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onPresenterCreated() {

    }
}
