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
    /**
     * User of which show the profile
     */
    private User modelUser;

    public ProfilePresenter(IUserRepository userRepository, String userId) {
        this.userId = userId;
        this.userRepository = userRepository;
    }

    private void showUserProfile() {
        if (modelUser != null) {
            getView().showProfilePicture(modelUser.getProfilePictureUri());
            getView().showUserEmail(modelUser.getEmail());
            getView().showUserName(modelUser.getName());

            if (userId.equals(userRepository.getCurrentUserId())) {
                getView().showEditProfileButton();
                getView().showSettingsIcon();
            } else {
                getView().showFollowButton();
            }
        }
    }

    /**
     * Load into {@link ProfilePresenter#modelUser} the user profile of the user currently signed into inforinfestigador
     */
    private void loadCurrentUser() {
        disposables.add(userRepository.getUser(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableMaybeObserver<User>() {
                    @Override
                    public void onSuccess(User user) {
                        modelUser = user;
                        showUserProfile();
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
    public void onStart() {
        super.onStart();
        showUserProfile();
    }

    @Override
    public void onPresenterCreated() {
        loadCurrentUser();
    }

    @Override
    public void editProfile() {
        getView().startEditProfileActivity(modelUser);
    }

    /**
     * When the user profile has been edited, reload the user from the db and update the view
     */
    @Override
    public void onProfileEdited() {
        loadCurrentUser();
        showUserProfile();
    }
}
