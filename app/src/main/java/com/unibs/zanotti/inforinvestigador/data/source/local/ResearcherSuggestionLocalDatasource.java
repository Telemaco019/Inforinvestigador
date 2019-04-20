package com.unibs.zanotti.inforinvestigador.data.source.local;

import com.unibs.zanotti.inforinvestigador.data.model.ResearcherSuggestion;
import com.unibs.zanotti.inforinvestigador.data.source.IResearcherSuggestionDatasource;
import com.unibs.zanotti.inforinvestigador.data.source.local.dao.IResearcherSuggestionLocalDao;
import com.unibs.zanotti.inforinvestigador.data.source.local.dao.impl.DummyResearcherSuggestionLocalDao;

import java.util.List;

public class ResearcherSuggestionLocalDatasource implements IResearcherSuggestionDatasource {
    private static volatile ResearcherSuggestionLocalDatasource INSTANCE;

    private IResearcherSuggestionLocalDao researcherSuggestionDao;

    public ResearcherSuggestionLocalDatasource() {
        researcherSuggestionDao = new DummyResearcherSuggestionLocalDao();
    }

    public static ResearcherSuggestionLocalDatasource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ResearcherSuggestionLocalDatasource();
        }
        return INSTANCE;
    }

    @Override
    public List<ResearcherSuggestion> getResearcherSuggestions() {
        return researcherSuggestionDao.getAll();
    }
}
