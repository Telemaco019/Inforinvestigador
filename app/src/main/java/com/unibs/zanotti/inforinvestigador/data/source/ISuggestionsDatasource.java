package com.unibs.zanotti.inforinvestigador.data.source;

import com.unibs.zanotti.inforinvestigador.data.model.PaperSuggestion;
import com.unibs.zanotti.inforinvestigador.data.model.ResearcherSuggestion;

import java.util.List;

/**
 * Main entry point for accessing suggestions data
 */
public interface ISuggestionsDatasource {
    List<PaperSuggestion> getPapersSuggestions();
    List<ResearcherSuggestion> getResearchersSuggestions();
}
