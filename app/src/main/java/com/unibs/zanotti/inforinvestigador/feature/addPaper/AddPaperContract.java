package com.unibs.zanotti.inforinvestigador.feature.addPaper;

import com.unibs.zanotti.inforinvestigador.baseMVP.BaseContract;
import com.unibs.zanotti.inforinvestigador.domain.model.Paper;

public interface AddPaperContract {
    interface Presenter extends BaseContract.Presenter<View> {

        void onSearchPaperButtonClicked(String doi);

        void onCancelButtonClicked();
    }

    interface View extends BaseContract.View {

        void showProgressBar();

        void hideProgressBar();

        void showMessageDoiNotFound();

        void showPaper(Paper paper);

        void hidePaper();

        void clearDoiTextfield();
    }
}
