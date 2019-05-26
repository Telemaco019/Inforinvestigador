package com.unibs.zanotti.inforinvestigador.homefeed;

import com.unibs.zanotti.inforinvestigador.baseMVP.BaseContract;
import com.unibs.zanotti.inforinvestigador.domain.model.FeedPaper;
import com.unibs.zanotti.inforinvestigador.domain.model.ResearcherSuggestion;

import java.util.List;

public interface HomefeedContract {
    interface Presenter extends BaseContract.Presenter<View> {
        void paperShareClicked(String paperShareId);

        void onRefresh();
    }

    interface View extends BaseContract.View {
        void showPapersFeed(List<FeedPaper> feedPapers);

        void showResearchersSuggestions(List<ResearcherSuggestion> suggestions);

        void showPaperDetails(String paperId);

        int getScrollPositionResearchersList();

        int getScrollPositionPapersList();

        void setScrollPositionResearchersList(int position);

        void setScrollPositionPapersList(int position);

        void showLoadingProgressBar();

        void hideLoadingProgressBar();
    }
}
