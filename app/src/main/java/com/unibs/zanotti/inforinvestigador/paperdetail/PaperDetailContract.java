package com.unibs.zanotti.inforinvestigador.paperdetail;

import com.unibs.zanotti.inforinvestigador.BasePresenter;
import com.unibs.zanotti.inforinvestigador.BaseView;

import java.util.List;

public interface PaperDetailContract {
    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<Presenter> {
        void showPaperTitle(String paperTitle);
        void showPaperAbstract(String paperAbstract);
        void showPaperAuthors(List<String> authors);
        void showPaperPublisher(String publisher);
        void showPaperCitations(String citations);
        void showPaperDOI(String DOI);
        void showPaperDate(String date);
        void showPaperImage(int resImageId);
    }
}
