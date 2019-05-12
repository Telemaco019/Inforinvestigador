package com.unibs.zanotti.inforinvestigador.homefeed;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.baseMVP.BaseFragment;
import com.unibs.zanotti.inforinvestigador.domain.model.FeedPaper;
import com.unibs.zanotti.inforinvestigador.domain.model.ResearcherSuggestion;
import com.unibs.zanotti.inforinvestigador.homefeed.adapters.PaperFeedAdapter;
import com.unibs.zanotti.inforinvestigador.homefeed.adapters.ResearcherSuggestionAdapter;
import com.unibs.zanotti.inforinvestigador.paperdetail.PaperDetailActivity;
import com.unibs.zanotti.inforinvestigador.utils.Actions;
import com.unibs.zanotti.inforinvestigador.utils.Injection;

import java.util.ArrayList;
import java.util.List;

public class HomefeedFragment extends BaseFragment<HomefeedContract.View, HomefeedContract.Presenter>
        implements HomefeedContract.View, PaperFeedAdapter.OnPaperShareListener {

    private PaperFeedAdapter paperFeedAdapter;
    private ResearcherSuggestionAdapter researcherSuggestionAdapter;
    private RecyclerView papersRecyclerView;
    private RecyclerView researchersRecyclerView;

    public HomefeedFragment() {
        paperFeedAdapter = new PaperFeedAdapter(new ArrayList<>(0), this);
        researcherSuggestionAdapter = new ResearcherSuggestionAdapter(new ArrayList<>(0));
    }

    public static Fragment newInstance() {
        return new HomefeedFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homefeed, container, false);

        // Setup researcher recycler
        researchersRecyclerView = view.findViewById(R.id.recommended_researchers_recycler);
        researchersRecyclerView.setHasFixedSize(true);
        researchersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        researchersRecyclerView.setAdapter(researcherSuggestionAdapter);

        // Setup paper recycler
        papersRecyclerView = view.findViewById(R.id.paper_shares_recycler);
        papersRecyclerView.setHasFixedSize(true);
        papersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        papersRecyclerView.setAdapter(paperFeedAdapter);

        return view;
    }

    @Override
    public void onPaperShareClick(String paperShareId) {
        this.presenter.paperShareClicked(paperShareId);
    }

    @Override
    public void showPapersFeed(List<FeedPaper> feedPapers) {
        this.paperFeedAdapter.setDataset(feedPapers);
        this.paperFeedAdapter.notifyDataSetChanged();
    }

    @Override
    public void showResearchersSuggestions(List<ResearcherSuggestion> suggestions) {
        this.researcherSuggestionAdapter.setDataset(suggestions);
    }

    @Override
    public void showPaperDetails(String paperId) {
        Intent intent = new Intent(Actions.SHOW_PAPER_DETAILS);
        intent.putExtra(PaperDetailActivity.STRING_EXTRA_PAPER_ID, paperId);
        startActivity(intent);
    }

    @Override
    public int getScrollPositionResearchersList() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) researchersRecyclerView.getLayoutManager();
        return layoutManager != null ? layoutManager.findLastCompletelyVisibleItemPosition() : 0;
    }

    @Override
    public int getScrollPositionPapersList() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) papersRecyclerView.getLayoutManager();
        return layoutManager != null ? layoutManager.findLastCompletelyVisibleItemPosition() : 0;
    }

    @Override
    public void setScrollPositionResearchersList(int position) {
        RecyclerView.LayoutManager layoutManager = researchersRecyclerView.getLayoutManager();
        if (layoutManager != null) {
            layoutManager.scrollToPosition(position);
        }
    }

    @Override
    public void setScrollPositionPapersList(int position) {
        RecyclerView.LayoutManager layoutManager = papersRecyclerView.getLayoutManager();
        if (layoutManager != null) {
            layoutManager.scrollToPosition(position);
        }
    }

    @Override
    protected HomefeedContract.Presenter createPresenter() {
        return new HomefeedPresenter(Injection.providePaperRepository(), Injection.provideUserRepository());
    }
}
