package com.unibs.zanotti.inforinvestigador.data.source.local.dao.impl;

import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.data.model.ResearcherSuggestion;
import com.unibs.zanotti.inforinvestigador.data.source.local.dao.IResearcherSuggestionLocalDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DummyResearcherSuggestionLocalDao implements IResearcherSuggestionLocalDao {
    @Override
    public void save(ResearcherSuggestion researcherSuggestion) {

    }

    @Override
    public void update(ResearcherSuggestion researcherSuggestion, String[] params) {

    }

    @Override
    public void delete(ResearcherSuggestion researcherSuggestion) {

    }

    @Override
    public List<ResearcherSuggestion> getAll() {
        List<ResearcherSuggestion> result = new ArrayList<>();

        result.add(
                new ResearcherSuggestion(
                        R.drawable.test_researcher_1,
                        "Maria Piras"
                )
        );
        result.add(
                new ResearcherSuggestion(
                        R.drawable.test_researcher_2,
                        "Leonor Freitas"
                )
        );
        result.add(
                new ResearcherSuggestion(
                        R.drawable.test_researcher_3,
                        "Antonio Lopes"
                )
        );
        result.add(
                new ResearcherSuggestion(
                        R.drawable.test_researcher_4,
                        "Cristiano Carvalho"
                )
        );
        result.add(
                new ResearcherSuggestion(
                        R.drawable.test_researcher_5,
                        "Teresa Sardinha"
                )
        );
        result.add(
                new ResearcherSuggestion(
                        R.drawable.test_researcher_6,
                        "Joana de Carvalho"
                )
        );
        result.add(
                new ResearcherSuggestion(
                        R.drawable.test_researcher_7,
                        "Mario Relha"
                )
        );

        return result;
    }

    @Override
    public Optional<ResearcherSuggestion> get(long id) {
        return Optional.empty();
    }
}
