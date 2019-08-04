package com.unibs.zanotti.inforinvestigador.feature.editPaper;

import com.unibs.zanotti.inforinvestigador.baseMVP.BaseContract;
import com.unibs.zanotti.inforinvestigador.domain.model.Paper;

public interface EditPaperShareContract {
    interface View extends BaseContract.View {
        void showPaper(Paper paper);

        void hideProgressBar();

        void showProgressBar();

        void showSavePaperError();

        void finishActivityWithResultOk();
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void saveButtonClicked(String updatedSharingUserComment);
    }
}
