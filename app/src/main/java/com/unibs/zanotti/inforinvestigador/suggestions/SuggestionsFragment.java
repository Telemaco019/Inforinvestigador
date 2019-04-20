package com.unibs.zanotti.inforinvestigador.suggestions;

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
import com.unibs.zanotti.inforinvestigador.data.model.PaperSuggestion;
import com.unibs.zanotti.inforinvestigador.data.model.ResearcherSuggestion;
import com.unibs.zanotti.inforinvestigador.suggestions.adapters.PaperSuggestionAdapter;
import com.unibs.zanotti.inforinvestigador.suggestions.adapters.ResearcherSuggestionAdapter;

import java.util.ArrayList;
import java.util.List;

public class SuggestionsFragment extends Fragment implements
        SuggesitonsContract.View,
        PaperSuggestionAdapter.OnPaperSuggestionListener {

    private SuggesitonsContract.Presenter presenter;
    private PaperSuggestionAdapter paperSuggestionAdapter;
    private ResearcherSuggestionAdapter researcherSuggestionAdapter;

    public SuggestionsFragment() {
        paperSuggestionAdapter = new PaperSuggestionAdapter(new ArrayList<>(0), this);
        researcherSuggestionAdapter = new ResearcherSuggestionAdapter(new ArrayList<>(0));
    }

    @Override
    public void setPresenter(SuggesitonsContract.Presenter presenter) {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Setup researcher recycler
        RecyclerView researchers_recycler = view.findViewById(R.id.recommended_researchers_recycler);
        researchers_recycler.setHasFixedSize(true);
        researchers_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        researchers_recycler.setAdapter(researcherSuggestionAdapter);

        // Setup paper recycler
        RecyclerView papers_recycler = view.findViewById(R.id.recommended_papers_recycler);
        papers_recycler.setHasFixedSize(true);
        papers_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        papers_recycler.setAdapter(paperSuggestionAdapter);

        return view;
    }

    @Override
    public void onPaperSuggestionClick(int suggestionNumber) {

    }

    @Override
    public void showPapersSuggestions(List<PaperSuggestion> suggestions) {
        this.paperSuggestionAdapter.setDataset(suggestions);
    }

    @Override
    public void showResearchersSuggestions(List<ResearcherSuggestion> suggestions) {
        this.researcherSuggestionAdapter.setDataset(suggestions);
    }
}
