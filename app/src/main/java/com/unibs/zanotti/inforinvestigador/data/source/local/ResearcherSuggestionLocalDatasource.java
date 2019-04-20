package com.unibs.zanotti.inforinvestigador.data.source.local;

import com.unibs.zanotti.inforinvestigador.data.model.ResearcherSuggestion;
import com.unibs.zanotti.inforinvestigador.data.source.IResearcherSuggestionDatasource;
import com.unibs.zanotti.inforinvestigador.data.source.local.dao.IResearcherSuggestionLocalDao;
import com.unibs.zanotti.inforinvestigador.data.source.local.dao.impl.DummyResearcherSuggestionLocalDao;

import java.util.List;

public class ResearcherSuggestionLocalDatasource implements IResearcherSuggestionDatasource {
    private IResearcherSuggestionLocalDao researcherSuggestionDao;

    public ResearcherSuggestionLocalDatasource() {
        researcherSuggestionDao = new DummyResearcherSuggestionLocalDao();
    }

    @Override
    public List<ResearcherSuggestion> getResearcherSuggestions() {
        return null;
    }
}
