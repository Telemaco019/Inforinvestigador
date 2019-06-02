package com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.followers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.baseMVP.BaseFragment;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.adapters.FollowersRecyclerAdapter;
import com.unibs.zanotti.inforinvestigador.utils.Injection;

import java.util.ArrayList;
import java.util.List;

public class ListFollowersFragment extends BaseFragment<ListFollowersContract.View, ListFollowersContract.Presenter>
        implements ListFollowersContract.View {

    private static final String FRAGMENT_STRING_ARGUMENT_USER_ID = "ListFollowersFragment.Arguments.UserId";

    @BindView(R.id.list_recycler_view)
    RecyclerView recyclerView;

    private FollowersRecyclerAdapter mAdapter;

    public ListFollowersFragment() {
        mAdapter = new FollowersRecyclerAdapter(new ArrayList<>(0));
    }

    public static ListFollowersFragment newInstance(String userId) {
        Bundle arguments = new Bundle();
        arguments.putString(FRAGMENT_STRING_ARGUMENT_USER_ID, userId);
        ListFollowersFragment fragment = new ListFollowersFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_list, container, false);
        ButterKnife.bind(this, view);

        // Setup recycler view
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        return view;
    }


    @Override
    protected ListFollowersContract.Presenter createPresenter() {
        String userId = getArguments().getString(FRAGMENT_STRING_ARGUMENT_USER_ID);
        return new ListFollowersPresenter(Injection.provideUserRepository(), userId);
    }

    @Override
    public void showFollowersList(List<User> followersList) {
        mAdapter.setDataset(followersList);
    }
}
