package com.unibs.zanotti.inforinvestigador.data.source;

import com.unibs.zanotti.inforinvestigador.data.model.ResearcherSuggestion;

import java.util.List;

// TODO: to be deleted (suggestion won't be stored in a db but will be computed)
public interface IResearcherSuggestionDatasource {
    List<ResearcherSuggestion> getResearcherSuggestions();
}
