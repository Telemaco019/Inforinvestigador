package com.unibs.zanotti.inforinvestigador.suggestions;

import com.unibs.zanotti.inforinvestigador.data.PaperSuggestion;
import com.unibs.zanotti.inforinvestigador.data.ResearcherSuggestion;
import com.unibs.zanotti.inforinvestigador.data.source.SuggestionsDataSource;

import java.util.List;

public class SuggestionsPresenter implements SuggesitonsContract.Presenter {

    private final SuggesitonsContract.View view;
    private SuggestionsDataSource suggestionsDataSource;

    public SuggestionsPresenter(SuggesitonsContract.View view, SuggestionsDataSource dataSource) {
        this.view = view;
        this.suggestionsDataSource = dataSource;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        loadSuggestions();
    }

    @Override
    public void loadSuggestions() {
        loadPapersSuggestions();
        loadResearchersSuggestions();
    }

    private void loadResearchersSuggestions() {
        List<PaperSuggestion> papersSuggestions = suggestionsDataSource.getPapersSuggestions();
        view.showPapersSuggestions(papersSuggestions);
    }

    private void loadPapersSuggestions() {
        List<ResearcherSuggestion> researchersSuggestions = suggestionsDataSource.getResearchersSuggestions();
        view.showResearchersSuggestions(researchersSuggestions);
    }
}
