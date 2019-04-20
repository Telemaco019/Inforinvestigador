package com.unibs.zanotti.inforinvestigador.suggestions;

import com.unibs.zanotti.inforinvestigador.data.model.PaperShare;
import com.unibs.zanotti.inforinvestigador.data.model.ResearcherSuggestion;
import com.unibs.zanotti.inforinvestigador.data.source.IPaperShareDatasource;

import java.util.List;

public class SuggestionsPresenter implements SuggesitonsContract.Presenter {

    private final SuggesitonsContract.View view;
    private IPaperShareDatasource suggestionsDataSource;

    public SuggestionsPresenter(SuggesitonsContract.View view, IPaperShareDatasource dataSource) {
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
        List<PaperShare> papersSuggestions = suggestionsDataSource.getPaperShares();
        view.showPapersSuggestions(papersSuggestions);
    }

    private void loadPapersSuggestions() {
        List<ResearcherSuggestion> researchersSuggestions = suggestionsDataSource.getResearchersSuggestions();
        view.showResearchersSuggestions(researchersSuggestions);
    }
}
