package com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.adapters.FollowingFollowersPageAdapter;
import org.jetbrains.annotations.NotNull;

public class ListFollowingAndFollowersFragment extends Fragment {
    private static final String FRAGMENT_PARCELABLE_ARGUMENT_USER = "ListFollowingAndFollowersFragment.USER";
    private static final String FRAGMENT_INT_ARGUMENT_INITIAL_SELECTED_TAB = "ListFollowingAndFollowersFragment.INITIAL_SELECTED_TAB";

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    public ListFollowingAndFollowersFragment() {
        // Required empty public constructor
    }

    public static ListFollowingAndFollowersFragment newInstance(User user, int initialSelectedTab) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(FRAGMENT_PARCELABLE_ARGUMENT_USER, user);
        arguments.putInt(FRAGMENT_INT_ARGUMENT_INITIAL_SELECTED_TAB, initialSelectedTab);
        ListFollowingAndFollowersFragment fragment = new ListFollowingAndFollowersFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_following_and_followers, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        User user = getArguments().getParcelable(FRAGMENT_PARCELABLE_ARGUMENT_USER);
        FollowingFollowersPageAdapter adapter = new FollowingFollowersPageAdapter(getChildFragmentManager(), user);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(getArguments().getInt(FRAGMENT_INT_ARGUMENT_INITIAL_SELECTED_TAB));
    }
}
