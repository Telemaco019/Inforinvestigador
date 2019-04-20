package com.unibs.zanotti.inforinvestigador.suggestions;

import com.unibs.zanotti.inforinvestigador.data.model.PaperSuggestion;
import com.unibs.zanotti.inforinvestigador.data.model.ResearcherSuggestion;
import com.unibs.zanotti.inforinvestigador.data.source.ISuggestionsDatasource;

import java.util.List;

public class SuggestionsPresenter implements SuggesitonsContract.Presenter {

    private final SuggesitonsContract.View view;
    private ISuggestionsDatasource suggestionsDataSource;

    public SuggestionsPresenter(SuggesitonsContract.View view, ISuggestionsDatasource dataSource) {
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
