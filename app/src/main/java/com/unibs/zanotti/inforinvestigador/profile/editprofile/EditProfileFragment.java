package com.unibs.zanotti.inforinvestigador.profile.editprofile;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.baseMVP.BaseFragment;
import com.unibs.zanotti.inforinvestigador.utils.Injection;


public class EditProfileFragment extends BaseFragment<EditProfileContract.View, EditProfileContract.Presenter> {

    private static final String FRAGMENT_STRING_ARGUMENT_USER_ID = "EditProfileFragment.USER_ID";

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    protected EditProfileContract.Presenter createPresenter() {
        return new EditProfilePresenter(Injection.provideUserRepository());
    }
}
