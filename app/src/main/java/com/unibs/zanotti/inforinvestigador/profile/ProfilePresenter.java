package com.unibs.zanotti.inforinvestigador.profile;

import com.unibs.zanotti.inforinvestigador.baseMVP.BasePresenter;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;

public class ProfilePresenter extends BasePresenter<ProfileContract.View> implements ProfileContract.Presenter {

    private final IUserRepository userRepository;
    private String userId;

    public ProfilePresenter(IUserRepository userRepository, String userId) {
        this.userId = userId;
        this.userRepository = userRepository;
    }

    private void showUserProfile() {
        disposables.add(userRepository.getUser(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableMaybeObserver<User>() {
                    @Override
                    public void onSuccess(User user) {
                        getView().showProfilePicture(user.getProfilePictureUri());
                        getView().showUserEmail(user.getEmail());
                        getView().showUserName(user.getName());
                        // mView.showUserPhoneNumber(user.get)
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void onPresenterCreated() {
        this.showUserProfile();
    }

    @Override
    public void onStart() {

    }
}
