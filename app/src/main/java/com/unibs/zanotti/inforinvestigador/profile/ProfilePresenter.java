package com.unibs.zanotti.inforinvestigador.profile;

import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableMaybeObserver;

public class ProfilePresenter implements ProfileContract.Presenter {

    private final IUserRepository userRepository;
    private final CompositeDisposable disposables;
    private ProfileContract.View mView;
    private String userId;

    public ProfilePresenter(ProfileContract.View mView, IUserRepository userRepository, String userId) {
        this.mView = mView;
        this.userId = userId;
        this.userRepository = userRepository;
        this.mView.setPresenter(this);
        disposables = new CompositeDisposable();
    }

    @Override
    public void start() {
        showUserProfile();
    }

    private void showUserProfile() {
        disposables.add(userRepository.getUser(userId).subscribeWith(new DisposableMaybeObserver<User>() {
            @Override
            public void onSuccess(User user) {
                mView.showProfilePicture(user.getProfilePictureUri());
                mView.showUserEmail(user.getEmail());
                mView.showUserName(user.getName());
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
}
