package com.unibs.zanotti.inforinvestigador.profile.editprofile;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.fragment.app.FragmentActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.baseMVP.BaseFragment;
import com.unibs.zanotti.inforinvestigador.utils.Injection;
import de.hdodenhof.circleimageview.CircleImageView;


public class EditProfileFragment extends BaseFragment<EditProfileContract.View, EditProfileContract.Presenter> implements EditProfileContract.View {

    private static final String FRAGMENT_STRING_ARGUMENT_USER_ID = "EditProfileFragment.USER_ID";
    private static final int PROFILE_PICTURE_QUALITY = 70;

    @BindView(R.id.edit_profile_iv_profile_picture)
    CircleImageView ivEditProfilePicture;
    @BindView(R.id.edit_profile_edit_profile_picture_button)
    ImageView ivEditProfilePictureButton;
    @BindView(R.id.edit_profile_progress_bar_profile_picture)
    ProgressBar progressBarUploadProfilePicture;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    public static EditProfileFragment newInstance(String userId) {
        Bundle arguments = new Bundle();
        arguments.putString(FRAGMENT_STRING_ARGUMENT_USER_ID, userId);
        EditProfileFragment fragment = new EditProfileFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            presenter.profilePictureEdited(resultCode, data);
        }
    }

    @OnClick(R.id.edit_profile_edit_profile_picture_button)
    public void editProfilePictureClicked() {
        if (getContext() != null) {
            CropImage.activity()
                    .setScaleType(CropImageView.ScaleType.CENTER_CROP)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setFixAspectRatio(true)
                    .setMultiTouchEnabled(true)
                    .setOutputCompressQuality(PROFILE_PICTURE_QUALITY)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(getContext(), this);
        }
    }

    @Override
    protected EditProfileContract.Presenter createPresenter() {
        return new EditProfilePresenter(Injection.provideUserRepository());
    }

    @Override
    public void showEditProfilePictureErrorMessage() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            Snackbar.make(activity.findViewById(R.id.profile_btn_editProfile),
                    getResources().getString(R.string.edit_profile_picture_error_msg),
                    Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void updateProgressBar(Double progress) {
        progressBarUploadProfilePicture.setVisibility(View.VISIBLE);
        progressBarUploadProfilePicture.setProgress(progress.intValue());
    }

    @Override
    public void showProfilePicture(Uri imageUri) {
        Picasso.get()
                .load(imageUri)
                .fit()
                .centerCrop()
                .noPlaceholder()
                .into(ivEditProfilePicture);
    }

    @Override
    public void hideProgressBarUploadProfilePicture() {
        progressBarUploadProfilePicture.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBarUploadProfilePicture() {
        progressBarUploadProfilePicture.setVisibility(View.VISIBLE);
    }
}
