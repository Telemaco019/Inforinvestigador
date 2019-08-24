package com.unibs.zanotti.inforinvestigador.feature.profile.listFollowingAndFollowers.followers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import com.unibs.zanotti.inforinvestigador.feature.profile.listFollowingAndFollowers.adapters.FollowersRecyclerViewAdapter;
import com.unibs.zanotti.inforinvestigador.feature.profile.listFollowingAndFollowers.base.FollowListBaseView;
import com.unibs.zanotti.inforinvestigador.utils.Injection;

import java.util.ArrayList;

public class ListFollowersFragment extends FollowListBaseView implements ListFollowersContract.View {

    private static final String FRAGMENT_PARCELABLE_ARGUMENT_USER = "ListFollowersFragment.Arguments.User";

    @BindView(R.id.list_recycler_view)
    RecyclerView recyclerView;

    public ListFollowersFragment() {
    }

    public static ListFollowersFragment newInstance(User user) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(FRAGMENT_PARCELABLE_ARGUMENT_USER, user);
        ListFollowersFragment fragment = new ListFollowersFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_list, container, false);
        ButterKnife.bind(this, view);

        User user = getArguments().getParcelable(FRAGMENT_PARCELABLE_ARGUMENT_USER);
        mAdapter = new FollowersRecyclerViewAdapter(new ArrayList<>(0),
                user.getId(),
                Injection.provideUserRepository(),
                this);

        // Setup recycler view
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        return view;
    }


    @Override
    protected ListFollowersContract.Presenter createPresenter() {
        User user = getArguments().getParcelable(FRAGMENT_PARCELABLE_ARGUMENT_USER);
        return new ListFollowersPresenter(Injection.provideUserRepository(), user);
    }
}
