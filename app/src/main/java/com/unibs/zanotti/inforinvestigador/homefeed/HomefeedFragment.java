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
import com.unibs.zanotti.inforinvestigador.common.Actions;
import com.unibs.zanotti.inforinvestigador.data.model.ExpandableCommentGroup;
import com.unibs.zanotti.inforinvestigador.data.model.PaperShare;
import com.unibs.zanotti.inforinvestigador.data.model.ResearcherSuggestion;
import com.unibs.zanotti.inforinvestigador.homefeed.adapters.PaperShareAdapter;
import com.unibs.zanotti.inforinvestigador.homefeed.adapters.ResearcherSuggestionAdapter;
import com.unibs.zanotti.inforinvestigador.paperdetail.PaperDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class HomefeedFragment extends Fragment implements
        HomefeedContract.View,
        PaperShareAdapter.OnPaperShareListener {

    private HomefeedContract.Presenter presenter;
    private PaperShareAdapter paperShareAdapter;
    private ResearcherSuggestionAdapter researcherSuggestionAdapter;

    public HomefeedFragment() {
        paperShareAdapter = new PaperShareAdapter(new ArrayList<>(0), this);
        researcherSuggestionAdapter = new ResearcherSuggestionAdapter(new ArrayList<>(0));
    }

    @Override
    public void setPresenter(HomefeedContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homefeed, container, false);

        // Setup researcher recycler
        RecyclerView researchers_recycler = view.findViewById(R.id.recommended_researchers_recycler);
        researchers_recycler.setHasFixedSize(true);
        researchers_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        researchers_recycler.setAdapter(researcherSuggestionAdapter);

        // Setup paper recycler
        RecyclerView papers_recycler = view.findViewById(R.id.paper_shares_recycler);
        papers_recycler.setHasFixedSize(true);
        papers_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        papers_recycler.setAdapter(paperShareAdapter);

        return view;
    }

    @Override
    public void onPaperShareClick(long paperShareId) {
        this.presenter.paperShareClicked(paperShareId);
    }

    @Override
    public void showPaperShares(List<PaperShare> suggestions) {
        this.paperShareAdapter.setDataset(suggestions);
    }

    @Override
    public void showResearchersSuggestions(List<ResearcherSuggestion> suggestions) {
        this.researcherSuggestionAdapter.setDataset(suggestions);
    }

    @Override
    public void showPaperDetails(long paperId, List<ExpandableCommentGroup> comments) {
        Intent intent = new Intent(Actions.SHOW_PAPER_DETAILS);
        intent.putExtra(PaperDetailActivity.EXTRA_PAPER_ID, paperId);
        startActivity(intent);
    }
}
