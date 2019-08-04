package com.unibs.zanotti.inforinvestigador.feature.profile.listSharedPapers;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.common.collect.Lists;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.baseMVP.BaseFragment;
import com.unibs.zanotti.inforinvestigador.domain.model.PaperShare;
import com.unibs.zanotti.inforinvestigador.feature.editPaper.EditPaperShareActivity;
import com.unibs.zanotti.inforinvestigador.feature.paperdetail.PaperDetailActivity;
import com.unibs.zanotti.inforinvestigador.feature.profile.ProfileActivity;
import com.unibs.zanotti.inforinvestigador.feature.profile.listSharedPapers.adapter.ListUserSharesAdapter;
import com.unibs.zanotti.inforinvestigador.utils.Actions;
import com.unibs.zanotti.inforinvestigador.utils.ActivityUtils;
import com.unibs.zanotti.inforinvestigador.utils.Injection;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListUserSharesFragment extends BaseFragment<ListUserSharesContract.View, ListUserSharesContract.Presenter>
        implements ListUserSharesContract.View, ListUserSharesAdapter.OnPaperShareListener {
    private static final String FRAGMENT_STRING_ARGUMENT_USER_ID = "ListUserSharesFragment.argument.USER_ID";

    @BindView(R.id.undetermined_progress_bar)
    FrameLayout progressBar;
    @BindView(R.id.list_user_shares_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.content_layout)
    NestedScrollView contentLayout;

    private ListUserSharesAdapter mAdapter;

    public ListUserSharesFragment() {
        mAdapter = new ListUserSharesAdapter(Lists.newArrayList(), this, Injection.provideUserRepository().getCurrentUserId());
    }

    public static ListUserSharesFragment newInstance(String userId) {
        Bundle arguments = new Bundle();
        arguments.putString(FRAGMENT_STRING_ARGUMENT_USER_ID, userId);
        ListUserSharesFragment fragment = new ListUserSharesFragment();
        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_user_shares, container, false);
        ButterKnife.bind(this, view);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    protected ListUserSharesContract.Presenter createPresenter() {
        return new ListUserSharesPresenter(getArguments().getString(FRAGMENT_STRING_ARGUMENT_USER_ID),
                Injection.providePaperRepository(),
                Injection.provideUserRepository());
    }

    @Override
    public void showProgressBar() {
        if (progressBar.getVisibility() != View.VISIBLE) {
            ActivityUtils.animateViewWithFade(progressBar, View.VISIBLE, 1f, ActivityUtils.FADE_ANIMATION_STANDARD_DURATION_MS);
        }
    }

    @Override
    public void showSharedPapers(List<PaperShare> sharedPapers) {
        mAdapter.setDataset(sharedPapers);
    }

    @Override
    public void hideProgressBar() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            ActivityUtils.animateViewWithFade(progressBar, View.GONE, 0f, ActivityUtils.FADE_ANIMATION_STANDARD_DURATION_MS);
        }
    }

    @Override
    public void showContentLayout() {
        if (contentLayout.getVisibility() != View.VISIBLE) {
            ActivityUtils.animateViewWithFade(contentLayout, View.VISIBLE, 1f, ActivityUtils.FADE_ANIMATION_STANDARD_DURATION_MS);
        }
    }

    @Override
    public void onPaperShareClicked(String paperId) {
        Intent intent = new Intent(Actions.SHOW_PAPER_DETAILS);
        intent.putExtra(PaperDetailActivity.STRING_EXTRA_PAPER_ID, paperId);
        startActivity(intent);
    }

    @Override
    public void onPaperSharingUserClick(String userId) {
        Intent intent = new Intent(Actions.SHOW_RESEARCHER_PROFILE);
        intent.putExtra(ProfileActivity.STRING_EXTRA_RESEARCHER_ID, userId);
        startActivity(intent);
    }

    @Override
    public void onEditPaperShareClicked(String paperId) {
        Intent intent = new Intent(Actions.EDIT_PAPER_SHARE);
        intent.putExtra(EditPaperShareActivity.STRING_EXTRA_PAPER_ID, paperId);
        startActivityForResult(intent, EditPaperShareActivity.EDIT_PAPER_SHARE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EditPaperShareActivity.EDIT_PAPER_SHARE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                presenter.paperEdited();
            }
        }
    }
}
