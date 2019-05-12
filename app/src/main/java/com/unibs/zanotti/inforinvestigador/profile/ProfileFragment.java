package com.unibs.zanotti.inforinvestigador.profile;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.squareup.picasso.Picasso;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.auth.LoginActivity;
import com.unibs.zanotti.inforinvestigador.baseMVP.BaseFragment;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.utils.Injection;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends BaseFragment<ProfileContract.View, ProfileContract.Presenter>
        implements ProfileContract.View {

    private static final String FRAGMENT_STRING_ARGUMENT_USER_ID = "ProfileFragment.argument.USER_ID";

    @BindView(R.id.profile_btn_follow)
    AppCompatButton btnFollow;
    @BindView(R.id.profile_email_tv)
    TextView tvEmail;
    @BindView(R.id.profile_followers_number_tv)
    TextView tvFollowersNumber;
    @BindView(R.id.profile_home_tv)
    TextView tvHome;
    @BindView(R.id.profile_following_number_tv)
    TextView tvFollowingNumber;
    @BindView(R.id.profile_phone_number_tv)
    TextView tvPhone;
    @BindView(R.id.profile_round_profile_picture)
    CircleImageView ciProfilePicture;
    @BindView(R.id.profile_name_tv)
    TextView tvName;
    @BindView(R.id.profile_settings_icon)
    ImageView profileSettingIcon;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void showProfilePicture(Uri profilePictureUri) {
        Picasso.get()
                .load(profilePictureUri)
                .noPlaceholder()
                .error(R.drawable.user_profle_pic_placeholder)
                .fit()
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

    @Override
    protected ProfileContract.Presenter createPresenter() {
        IUserRepository userRepository = Injection.provideUserRepository();
        return new ProfilePresenter(userRepository, userRepository.getCurrentUserId());
    }
}
