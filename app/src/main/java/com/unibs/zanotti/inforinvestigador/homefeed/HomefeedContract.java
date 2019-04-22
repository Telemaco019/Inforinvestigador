package com.unibs.zanotti.inforinvestigador.homefeed;

import com.unibs.zanotti.inforinvestigador.BasePresenter;
import com.unibs.zanotti.inforinvestigador.BaseView;
import com.unibs.zanotti.inforinvestigador.data.model.ResearcherSuggestion;
import com.unibs.zanotti.inforinvestigador.domain.model.FeedPaper;

import java.util.List;

public interface HomefeedContract {
    interface Presenter extends BasePresenter {
        void loadFeed();

        void paperShareClicked(long paperShareId);
    }

    interface View extends BaseView<Presenter> {
        void showPapers(List<FeedPaper> feedPapers);

        void showResearchersSuggestions(List<ResearcherSuggestion> suggestions);

        void showPaperDetails(long paperId);
    }
}
