package com.unibs.zanotti.inforinvestigador.feature.homefeed;

import com.unibs.zanotti.inforinvestigador.baseMVP.BaseContract;
import com.unibs.zanotti.inforinvestigador.domain.model.PaperShare;
import com.unibs.zanotti.inforinvestigador.domain.model.ResearcherSuggestion;

import java.util.List;

public interface HomefeedContract {
    interface Presenter extends BaseContract.Presenter<View> {
        void paperShareClicked(String paperId);

        void researcherSuggestionClicked(String researcherId);

        void onRefresh();

        void paperShareDismissed(String paperId);
    }

    interface View extends BaseContract.View {
        void showPapersFeed(List<PaperShare> paperShares);

        void showResearchersSuggestions(List<ResearcherSuggestion> suggestions);

        void showPaper(String paperId);

        int getScrollPositionResearchersList();

        int getScrollPositionPapersList();

        void setScrollPositionResearchersList(int position);

        void setScrollPositionPapersList(int position);

        void showLoadingProgressBar();

        void hideLoadingProgressBar();

        void showResearcherProfile(String researcherId);

        void showContentLayout();

        void hideResearcherSuggestionsCaption();
    }
}
