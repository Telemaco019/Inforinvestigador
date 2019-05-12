package com.unibs.zanotti.inforinvestigador.homefeed;

import com.unibs.zanotti.inforinvestigador.baseMVP.BaseContract;
import com.unibs.zanotti.inforinvestigador.domain.model.FeedPaper;
import com.unibs.zanotti.inforinvestigador.domain.model.ResearcherSuggestion;

import java.util.List;

public interface HomefeedContract {
    interface Presenter extends BaseContract.Presenter<View> {
        void loadFeed();

        void paperShareClicked(String paperShareId);
    }

    interface View extends BaseContract.View {
        void showPapersFeed(List<FeedPaper> feedPapers);

        void showResearchersSuggestions(List<ResearcherSuggestion> suggestions);

        void showPaperDetails(String paperId);
    }
}
