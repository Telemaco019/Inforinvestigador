package com.unibs.zanotti.inforinvestigador.profile.editprofile;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import com.theartofdev.edmodo.cropper.CropImage;
import com.unibs.zanotti.inforinvestigador.baseMVP.BasePresenter;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

public class EditProfilePresenter extends BasePresenter<EditProfileContract.View> implements EditProfileContract.Presenter {
    private static final String TAG = String.valueOf(EditProfilePresenter.class);

    private IUserRepository userRepository;

    public EditProfilePresenter(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onPresenterCreated() {

    }

    @Override
    public void profilePictureEdited(int resultCode, Intent data) {
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (resultCode == RESULT_OK) {
            Uri imageUri = result.getUri();
            // Update view (show new profile picture)
            getView().showProfilePicture(imageUri);
            // Show progress bar while storing the new pic
            getView().showProgressBarUploadProfilePicture();
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
                            getView().hideProgressBarUploadProfilePicture();
                        }
                    }));
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Exception error = result.getError();
            Log.e(TAG, "Error editing profile picture", error);
            getView().showEditProfilePictureErrorMessage();
        }
    }
}
