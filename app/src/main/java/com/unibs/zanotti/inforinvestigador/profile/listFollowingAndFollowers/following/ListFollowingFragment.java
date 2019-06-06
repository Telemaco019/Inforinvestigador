package com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.following;

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
import com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.adapters.FollowingRecyclerViewAdapter;
import com.unibs.zanotti.inforinvestigador.utils.Injection;

import java.util.ArrayList;
import java.util.List;

public class ListFollowingFragment extends BaseFragment<ListFollowingContract.View, ListFollowingContract.Presenter>
        implements ListFollowingContract.View {

    private static final String FRAGMENT_PARCELABLE_ARGUMENT_USER_ID = "ListFollowingFragment.Arguments.User";

    @BindView(R.id.list_recycler_view)
    RecyclerView recyclerView;

    private final FollowingRecyclerViewAdapter mAdapter;

    public ListFollowingFragment() {
        mAdapter = new FollowingRecyclerViewAdapter(new ArrayList<>(0));
    }

    public static ListFollowingFragment newInstance(User user) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(FRAGMENT_PARCELABLE_ARGUMENT_USER_ID, user);
        ListFollowingFragment fragment = new ListFollowingFragment();
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
    protected ListFollowingContract.Presenter createPresenter() {
        User user = getArguments().getParcelable(FRAGMENT_PARCELABLE_ARGUMENT_USER_ID);
        return new ListFollowingPresenter(Injection.provideUserRepository(), user.getId());
    }

    @Override
    public void showFollowingList(List<User> followingUsersList) {
        mAdapter.setDataset(followingUsersList);
    }
}
