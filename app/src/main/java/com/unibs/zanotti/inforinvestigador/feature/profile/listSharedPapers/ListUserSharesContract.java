package com.unibs.zanotti.inforinvestigador.feature.profile.listSharedPapers;

import com.unibs.zanotti.inforinvestigador.baseMVP.BaseContract;
import com.unibs.zanotti.inforinvestigador.domain.model.FeedPaper;

import java.util.List;

public interface ListUserSharesContract {
    interface View extends BaseContract.View {

        void showProgressBar();

        void showSharedPapers(List<FeedPaper> sharedPapers);

        void hideProgressBar();

        void showContentLayout();
    }

    interface Presenter extends BaseContract.Presenter<View> {

    }
}
