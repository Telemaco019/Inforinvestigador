package com.unibs.zanotti.inforinvestigador.profile;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.auth.LoginActivity;
import com.unibs.zanotti.inforinvestigador.baseMVP.BaseFragment;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import com.unibs.zanotti.inforinvestigador.profile.editprofile.EditProfileActivity;
import com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.ListFollowingAndFollowersActivity;
import com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.adapters.FollowingFollowersPageAdapter;
import com.unibs.zanotti.inforinvestigador.utils.Actions;
import com.unibs.zanotti.inforinvestigador.utils.ActivityUtils;
import com.unibs.zanotti.inforinvestigador.utils.Injection;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends BaseFragment<ProfileContract.View, ProfileContract.Presenter>
        implements ProfileContract.View {

    private static final String FRAGMENT_STRING_ARGUMENT_USER_ID = "ProfileFragment.argument.USER_ID";

    @BindView(R.id.profile_btn_follow)
    AppCompatButton btnProfileFollow;
    @BindView(R.id.profile_btn_unfollow)
    AppCompatButton btnProfileUnfollow;
    @BindView(R.id.profile_email_tv)
    TextView tvEmail;
    @BindView(R.id.profile_followers_number_tv)
    TextView tvFollowersNumber;
    @BindView(R.id.profile_location_tv)
    TextView tvLocation;
    @BindView(R.id.profile_following_number_tv)
    TextView tvFollowingNumber;
    @BindView(R.id.profile_shared_paper_number_tv)
    TextView tvSharedPapersNumber;
    @BindView(R.id.profile_phone_number_tv)
    TextView tvPhone;
    @BindView(R.id.profile_round_profile_picture)
    CircleImageView ciProfilePicture;
    @BindView(R.id.profile_name_tv)
    TextView tvName;
    @BindView(R.id.profile_settings_icon)
    ImageView profileSettingIcon;
    @BindView(R.id.profile_btn_editProfile)
    MaterialButton btnEditProfile;
    @BindView(R.id.profile_phone_field_layout)
    LinearLayout phoneFieldLayout;
    @BindView(R.id.profile_location_field_layout)
    LinearLayout locationFieldLayout;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String userId) {
        Bundle arguments = new Bundle();
        arguments.putString(FRAGMENT_STRING_ARGUMENT_USER_ID, userId);
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void showProfilePicture(Uri profilePictureUri) {
        Picasso.get()
                .load(profilePictureUri)
                .fit()
                .centerCrop()
                .noPlaceholder()
                .into(ciProfilePicture);
    }

    @Override
    public void showUserName(String name) {
        tvName.setText(name);
    }

    @Override
    public void showUserEmail(String email) {
        tvEmail.setText(email);
    }

    @Override
    public void showSettingsIcon() {
        profileSettingIcon.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEditProfileButton() {
        btnEditProfile.setVisibility(View.VISIBLE);
    }

    @Override
    public void startEditProfileActivity(User user) {
        Intent intent = new Intent(Actions.EDIT_PROFILE);
        intent.putExtra(EditProfileActivity.PARCELABLE_EXTRA_USER, user);
        startActivityForResult(intent, EditProfileActivity.EDIT_PROFILE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void showUserLocation(String location) {
        locationFieldLayout.setVisibility(View.VISIBLE);
        tvLocation.setText(location);
    }

    @Override
    public void showUserPhone(String phone) {
        phoneFieldLayout.setVisibility(View.VISIBLE);
        tvPhone.setText(phone);
    }

    @Override
    public void hideUserLocationField() {
        locationFieldLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideUserPhoneField() {
        phoneFieldLayout.setVisibility(View.GONE);
    }

    @Override
    public void showUserSharesNumber(int sharesNumber) {
        tvSharedPapersNumber.setText(String.valueOf(sharesNumber));
    }

    @Override
    public void showUserFollowersNumber(int followersNumber) {
        tvFollowersNumber.setText(String.valueOf(followersNumber));
    }

    @Override
    public void showUserFollowingNumber(int followingNumber) {
        tvFollowingNumber.setText(String.valueOf(followingNumber));
    }

    @Override
    public void showEmptyUserProfile() {
        btnEditProfile.setVisibility(View.GONE);
        btnProfileFollow.setVisibility(View.GONE);
        btnProfileUnfollow.setVisibility(View.GONE);
    }

    @Override
    public void replaceButtonFollowWithUnfollow() {
        ActivityUtils.substituteViewWithFade(btnProfileFollow, btnProfileUnfollow, 300);
    }

    @Override
    public void replaceButtonUnfollowWithFollow() {
        ActivityUtils.substituteViewWithFade(btnProfileUnfollow, btnProfileFollow, 300);
    }

    @Override
    public void showFollowingList(User user) {
        Intent intent = new Intent(getContext(), ListFollowingAndFollowersActivity.class);
        intent.putExtra(ListFollowingAndFollowersActivity.PARCELABLE_EXTRA_USER, user);
        intent.putExtra(ListFollowingAndFollowersActivity.INT_EXTRA_INITIAL_SELECTED_TAB,
                FollowingFollowersPageAdapter.POSITION_TAB_FOLLOWING_LIST);
        startActivityForResult(intent, ListFollowingAndFollowersActivity.LIST_FOLLOWING_AND_FOLLOWERS_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void showFollowersList(User user) {
        Intent intent = new Intent(getContext(), ListFollowingAndFollowersActivity.class);
        intent.putExtra(ListFollowingAndFollowersActivity.PARCELABLE_EXTRA_USER, user);
        intent.putExtra(ListFollowingAndFollowersActivity.INT_EXTRA_INITIAL_SELECTED_TAB,
                FollowingFollowersPageAdapter.POSITION_TAB_FOLLOWERS_LIST);
        startActivityForResult(intent, ListFollowingAndFollowersActivity.LIST_FOLLOWING_AND_FOLLOWERS_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void showSharedPapersList(String userId) {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EditProfileActivity.EDIT_PROFILE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                presenter.onProfileEdited();
            }
        }

        if(requestCode == ListFollowingAndFollowersActivity.LIST_FOLLOWING_AND_FOLLOWERS_ACTIVITY_REQUEST_CODE) {
            if(resultCode == ListFollowingAndFollowersActivity.FOLLOWING_OR_FOLLOWERS_UPDATED_RC) {
                presenter.onProfileEdited();
            }
        }
    }

    @OnClick(R.id.profile_btn_editProfile)
    public void onEditProfileClicked() {
        presenter.editProfile();
    }

    // TODO: to be removed/updated
    @OnClick(R.id.profile_settings_icon)
    public void logout() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.putExtra(LoginActivity.BOOLEAN_EXTRA_DO_LOGOUT, true);
        startActivity(intent);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.finish();
        }
    }

    @OnClick(R.id.profile_btn_follow)
    public void onFollowButtonClicked() {
        presenter.onFollowButtonClicked();
    }

    @OnClick(R.id.profile_btn_unfollow)
    public void onUnfollowButtonClicked() {
        presenter.onUnfollowButtonClicked();
    }

    @OnClick(R.id.profile_followers_number_tv)
    public void onFollowersNumberClicked() {
        presenter.onFollowersNumberClicked();
    }

    @OnClick(R.id.profile_following_number_tv)
    public void onFollowingNumberClicked() {
        presenter.onFollowingNumberClicked();
    }

    @OnClick(R.id.profile_shared_paper_number_tv)
    public void onSharedPapersNumberClicked() {
        presenter.onSharedPapersNumberClicked();
    }

    @Override
    protected ProfileContract.Presenter createPresenter() {
        IUserRepository userRepository = Injection.provideUserRepository();
        return new ProfilePresenter(userRepository, getArguments().getString(FRAGMENT_STRING_ARGUMENT_USER_ID));
    }
}
