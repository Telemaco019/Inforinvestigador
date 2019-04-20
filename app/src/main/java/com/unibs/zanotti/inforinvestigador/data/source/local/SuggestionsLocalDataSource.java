package com.unibs.zanotti.inforinvestigador.data.source.local;

import com.unibs.zanotti.inforinvestigador.data.model.PaperSuggestion;
import com.unibs.zanotti.inforinvestigador.data.model.ResearcherSuggestion;
import com.unibs.zanotti.inforinvestigador.data.source.ISuggestionsDatasource;

import java.util.List;

public class SuggestionsLocalDataSource implements ISuggestionsDatasource {

    private static volatile SuggestionsLocalDataSource INSTANCE;

    private DummyPapersSuggestionsDAO papersSuggestionsDAO;
    private DummyResearchersSuggestionsDAO researcherSuggestionsDAO;

    private SuggestionsLocalDataSource() {
       papersSuggestionsDAO = new DummyPapersSuggestionsDAO();
       researcherSuggestionsDAO = new DummyResearchersSuggestionsDAO();
    }

    public static SuggestionsLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SuggestionsLocalDataSource();
        }
        return INSTANCE;
    }

    @Override
    public List<PaperSuggestion> getPapersSuggestions() {
        return papersSuggestionsDAO.getSuggestions();
    }

    @Override
    public List<ResearcherSuggestion> getResearchersSuggestions() {
        return researcherSuggestionsDAO.getSuggestions();
    }
}
