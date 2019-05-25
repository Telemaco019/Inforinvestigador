package com.unibs.zanotti.inforinvestigador.profile.editprofile;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import com.theartofdev.edmodo.cropper.CropImage;
import com.unibs.zanotti.inforinvestigador.baseMVP.BasePresenter;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import com.unibs.zanotti.inforinvestigador.utils.FirebaseUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

public class EditProfilePresenter extends BasePresenter<EditProfileContract.View> implements EditProfileContract.Presenter {
    private static final String TAG = String.valueOf(EditProfilePresenter.class);

    private IUserRepository userRepository;
    private User modelUser;

    /**
     * If true, then the presenter is saving the profile picture to the db. Used to avoid exiting the activity with a
     * "confirm" (e.g. saving all the other fields) without the profile picture saving being complete
     */
    private boolean savingProfilePicture = false;

    public EditProfilePresenter(IUserRepository userRepository, User modelUser) {
        this.userRepository = userRepository;
        this.modelUser = modelUser;
    }

    @Override
    public void onPresenterCreated() {
        // NO OP
    }

    @Override
    public void onProfilePictureEdited(int resultCode, Intent data) {
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (resultCode == RESULT_OK) {
            Uri imageUri = result.getUri();
            // Update view (show new profile picture)
            getView().showProfilePicture(imageUri);
            // Show progress bar while storing the new pic
            getView().showProgressBarUploadProfilePicture();
            // Update flag
            savingProfilePicture = true;
            // Store new profile picture
            disposables.add(userRepository.updateUserProfilePicture(userRepository.getCurrentUserId(), imageUri)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<Double>() {
                        @Override
                        public void onNext(Double progress) {
                            Log.e(TAG, progress + "");
                            getView().updateProgressBar(progress);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                            getView().setActivityResult(RESULT_OK);
                            getView().hideProgressBarUploadProfilePicture();
                            getView().showProfilePicture(imageUri);
                            savingProfilePicture = false;
                        }
                    }));
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Exception error = result.getError();
            Log.e(TAG, "Error editing profile picture", error);
            getView().showEditProfilePictureErrorMessage();
        }
    }

    @Override
    public void updateUserProfileFields(String name, String phone, String location) {
        // Update all the other fields only if the profile picture is not being updated
        if(!savingProfilePicture) {
            getView().showProgressSavingUserProfileFields();
            String userId = modelUser.getId();
            disposables.add(userRepository.updateUserField(userId, FirebaseUtils.FIRESTORE_DOCUMENT_USER_FIELD_NAME, name.trim())
                    .andThen(userRepository.updateUserField(userId, FirebaseUtils.FIRESTORE_DOCUMENT_USER_FIELD_PHONE, phone.trim()))
                    .andThen(userRepository.updateUserField(userId, FirebaseUtils.FIRESTORE_DOCUMENT_USER_FIELD_LOCATION, location.trim()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableCompletableObserver() {
                        @Override
                        public void onComplete() {
                            getView().hideProgressBarSavingUserProfileFields();
                            getView().setActivityResult(RESULT_OK);
                            getView().finishActivity();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "Error updating user profile fields: ", e);
                        }
                    }));
        } else {
            getView().showMessageWaitProfilePictureSaving();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        refreshView();
    }

    /**
     * Show in the view the information associated to the current {@link EditProfilePresenter#modelUser user} which profile
     * is being edited
     */
    private void refreshView() {
        getView().showProfilePicture(modelUser.getProfilePictureUri());
        getView().showUserName(modelUser.getName());
        getView().showUserLocation(modelUser.getLocation());
        getView().showUserPhone(modelUser.getPhone());
    }
}
