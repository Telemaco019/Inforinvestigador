package com.unibs.zanotti.inforinvestigador.profile;

import android.util.Log;
import com.unibs.zanotti.inforinvestigador.baseMVP.BasePresenter;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import com.unibs.zanotti.inforinvestigador.domain.utils.StringUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ProfilePresenter extends BasePresenter<ProfileContract.View> implements ProfileContract.Presenter {
    private static final String TAG = String.valueOf(ProfilePresenter.class);

    private final IUserRepository userRepository;
    /**
     * Id of the user of which show the profile
     */
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
            getView().showUserSharesNumber(modelUser.getSharesNumber());
            getView().showUserFollowersNumber(modelUser.getFollowersNumber());
            getView().showUserFollowingNumber(modelUser.getFollowingNumber());

            if (StringUtils.isNotBlank(modelUser.getLocation())) {
                getView().showUserLocation(modelUser.getLocation());
            } else {
                getView().hideUserLocationField();
            }

            if (StringUtils.isNotBlank(modelUser.getPhone())) {
                getView().showUserPhone(modelUser.getPhone());
            } else {
                getView().hideUserPhoneField();
            }

            if (userId.equals(userRepository.getCurrentUserId())) {
                getView().showEditProfileButton();
                getView().showSettingsIcon();
            } else {
                showFollowUnfollowButton();
            }
        } else {
            getView().showEmptyUserProfile();
        }
    }

    /**
     * If the current user is already following the {@link ProfilePresenter#modelUser}, then shows the Follow/Unfollow
     * button with the Unfollow style, otherwise show it with the Follow style.
     * <p>It is assumed that the current user is different from {@link ProfilePresenter#modelUser}</p>
     */
    private void showFollowUnfollowButton() {
        disposables.add(userRepository.isFollowing(userRepository.getCurrentUserId(), userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {
                        if (result) {
                            getView().replaceButtonUnfollowWithFollow();
                        } else {
                            getView().replaceButtonFollowWithUnfollow();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error: ", e);
                    }
                }));
    }

    private void loadModelUser() {
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
        loadModelUser();
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
        loadModelUser();
        showUserProfile();
    }

    @Override
    public void onFollowButtonClicked() {
        // Update view first
        getView().replaceButtonFollowWithUnfollow();

        // Update data layer
        disposables.add(userRepository.followUser(userRepository.getCurrentUserId(), userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        // NO OP
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error following user: ", e);
                    }
                }));
    }

    @Override
    public void onUnfollowButtonClicked() {
        // Update view first
        getView().replaceButtonUnfollowWithFollow();

        // Update data layer
        disposables.add(userRepository.unfollowUser(userRepository.getCurrentUserId(), userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        // NO OP
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error unfollowing user: ", e);
                    }
                }));
    }
}
