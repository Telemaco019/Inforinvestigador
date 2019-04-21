package com.unibs.zanotti.inforinvestigador.homefeed;

import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.data.model.Comment;
import com.unibs.zanotti.inforinvestigador.data.model.PaperShare;
import com.unibs.zanotti.inforinvestigador.data.model.ResearcherSuggestion;
import com.unibs.zanotti.inforinvestigador.data.source.IPaperShareDatasource;

import java.util.ArrayList;
import java.util.List;

public class HomefeedPresenter implements HomefeedContract.Presenter {

    private final HomefeedContract.View view;
    private IPaperShareDatasource paperSharesDatasource;

    public HomefeedPresenter(HomefeedContract.View view,
                             IPaperShareDatasource dataSource) {
        this.view = view;
        this.paperSharesDatasource = dataSource;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        loadFeed();
    }

    @Override
    public void loadFeed() {
        loadPaperShares();
        loadResearchersSuggestions();
    }

    @Override
    public void paperShareClicked(long paperShareId) {
        List<Comment> comments = paperSharesDatasource.getComments(paperShareId);
        view.showPaperDetails(paperShareId, comments);
    }

    private void loadResearchersSuggestions() {
        List<ResearcherSuggestion> researchersSuggestions = computeResearcherSuggestions(1);
        view.showResearchersSuggestions(researchersSuggestions);
    }

    /**
     * Computes the suggestions for the user with the id provided as parameter
     *
     * @param userId
     * @return
     */
    private List<ResearcherSuggestion> computeResearcherSuggestions(int userId) {
        // Dummy implementation

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

    private void loadPaperShares() {
        List<PaperShare> papersSuggestions = paperSharesDatasource.getPaperShares();
        view.showPaperShares(papersSuggestions);
    }
}
