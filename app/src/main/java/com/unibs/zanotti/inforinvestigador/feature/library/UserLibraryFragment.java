package com.unibs.zanotti.inforinvestigador.feature.library;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.unibs.zanotti.inforinvestigador.utils.Actions;
import com.unibs.zanotti.inforinvestigador.utils.ActivityUtils;
import com.unibs.zanotti.inforinvestigador.utils.Injection;
import com.unibs.zanotti.inforinvestigador.utils.PaperShareRVAdapter;
import com.unibs.zanotti.inforinvestigador.utils.itemTouch.SimpleItemTouchHelperCallback;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class UserLibraryFragment
        extends BaseFragment<UserLibraryContract.View, UserLibraryContract.Presenter>
        implements UserLibraryContract.View, PaperShareRVAdapter.OnPaperShareListener {

    private static final String FRAGMENT_STRING_ARGUMENT_USER_ID = "UserLibraryFragment.Argument.String.userId";

    @BindView(R.id.list_user_papers_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.undetermined_progress_bar)
    FrameLayout progressBar;
    @BindView(R.id.content_layout)
    NestedScrollView contentLayout;
    @BindView(R.id.user_library_empty_library_caption)
    TextView emptyLibraryCaptionTv;

    private PaperShareRVAdapter mAdapter;

    public UserLibraryFragment() {
        mAdapter = new PaperShareRVAdapter(Lists.newArrayList(), this, Injection.provideUserRepository().getCurrentUserId());
    }

    public static UserLibraryFragment newInstance(String userId) {
        Bundle arguments = new Bundle();
        arguments.putString(FRAGMENT_STRING_ARGUMENT_USER_ID, userId);
        UserLibraryFragment fragment = new UserLibraryFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_library, container, false);
        ButterKnife.bind(this, view);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter, false, true);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);

        return view;
    }

    @Override
    protected UserLibraryContract.Presenter createPresenter() {
        return new UserLibraryPresenter(Injection.providePaperRepository(),
                Injection.provideUserRepository(),
                (String) getArguments().get(FRAGMENT_STRING_ARGUMENT_USER_ID));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            presenter.loadLibraryPapers();
        }
    }

    @Override
    public void showEmptyLibraryMessage() {
        if (emptyLibraryCaptionTv.getVisibility() != View.VISIBLE) {
            ActivityUtils.animateViewWithFade(emptyLibraryCaptionTv, View.VISIBLE, 1f, ActivityUtils.FADE_ANIMATION_STANDARD_DURATION_MS);
        }
    }

    @Override
    public void showProgressBar() {
        if (progressBar.getVisibility() != View.VISIBLE) {
            ActivityUtils.animateViewWithFade(progressBar, View.VISIBLE, 1f, ActivityUtils.FADE_ANIMATION_STANDARD_DURATION_MS);
        }
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
    public void showLibraryPapers(List<PaperShare> libraryPapers) {
        mAdapter.setDataset(libraryPapers);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void hideEmptyLibraryMessage() {
        if (emptyLibraryCaptionTv.getVisibility() == View.VISIBLE) {
            ActivityUtils.animateViewWithFade(emptyLibraryCaptionTv, View.GONE, 0f, ActivityUtils.FADE_ANIMATION_STANDARD_DURATION_MS);
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
    public void onPaperShareDismissed(@NotNull String paperId) {
        presenter.paperShareDismissed(paperId);
    }

    @Override
    public void onEditPaperShareClicked(String paperId) {
        Intent intent = new Intent(Actions.EDIT_PAPER_SHARE);
        intent.putExtra(EditPaperShareActivity.STRING_EXTRA_PAPER_ID, paperId);
        startActivityForResult(intent, EditPaperShareActivity.EDIT_PAPER_SHARE_ACTIVITY_REQUEST_CODE);
    }

}
