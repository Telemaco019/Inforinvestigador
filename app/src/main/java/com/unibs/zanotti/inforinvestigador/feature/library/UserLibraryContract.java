package com.unibs.zanotti.inforinvestigador.feature.library;

import com.unibs.zanotti.inforinvestigador.baseMVP.BaseContract;
import com.unibs.zanotti.inforinvestigador.domain.model.PaperShare;

import java.util.List;

public interface UserLibraryContract {
    interface View extends BaseContract.View {
        void showProgressBar();

        void hideProgressBar();

        void showContentLayout();

        void showLibraryPapers(List<PaperShare> libraryPapers);
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void paperShareDismissed(String paperId);
    }
}
