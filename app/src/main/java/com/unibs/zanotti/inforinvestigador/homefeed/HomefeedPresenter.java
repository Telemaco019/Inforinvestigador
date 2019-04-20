package com.unibs.zanotti.inforinvestigador.homefeed;

import com.unibs.zanotti.inforinvestigador.data.model.PaperShare;
import com.unibs.zanotti.inforinvestigador.data.model.ResearcherSuggestion;
import com.unibs.zanotti.inforinvestigador.data.source.IPaperShareDatasource;
import com.unibs.zanotti.inforinvestigador.data.source.IResearcherSuggestionDatasource;

import java.util.List;

public class HomefeedPresenter implements HomefeedContract.Presenter {

    private final HomefeedContract.View view;
    private IPaperShareDatasource paperSharesDatasource;
    private IResearcherSuggestionDatasource researcherSuggestionDatasource;

    public HomefeedPresenter(HomefeedContract.View view,
                             IPaperShareDatasource dataSource,
                             IResearcherSuggestionDatasource researcherSuggestionDatasource) {
        this.view = view;
        this.paperSharesDatasource = dataSource;
        this.researcherSuggestionDatasource = researcherSuggestionDatasource;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        loadSuggestions();
    }

    @Override
    public void loadSuggestions() {
        loadPaperShares();
        loadResearchersSuggestions();
    }

    private void loadResearchersSuggestions() {
        List<ResearcherSuggestion> researchersSuggestions = researcherSuggestionDatasource.getResearcherSuggestions();
        view.showResearchersSuggestions(researchersSuggestions);
    }

    private void loadPaperShares() {
        List<PaperShare> papersSuggestions = paperSharesDatasource.getPaperShares();
        view.showPapersSuggestions(papersSuggestions);
    }
}
