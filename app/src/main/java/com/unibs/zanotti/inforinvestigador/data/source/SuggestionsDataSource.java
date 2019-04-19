package com.unibs.zanotti.inforinvestigador.data.source;

import com.unibs.zanotti.inforinvestigador.data.PaperSuggestion;
import com.unibs.zanotti.inforinvestigador.data.ResearcherSuggestion;

import java.util.List;

/**
 * Main entry point for accessing suggestions data
 */
public interface SuggestionsDataSource {
    List<PaperSuggestion> getPapersSuggestions();
    List<ResearcherSuggestion> getResearchersSuggestions();
}
