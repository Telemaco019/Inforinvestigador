package com.unibs.zanotti.inforinvestigador.paperdetail;

import com.unibs.zanotti.inforinvestigador.BasePresenter;
import com.unibs.zanotti.inforinvestigador.BaseView;
import com.unibs.zanotti.inforinvestigador.data.model.Comment;

import java.util.List;

public interface PaperDetailContract {
    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<Presenter> {
        void showPaperTitle(String paperTitle);

        void showPaperAbstract(String paperAbstract);

        void showPaperAuthors(List<String> authors);

        void showPaperPublisher(String publisher);

        void showPaperCitations(int citations);

        void showPaperTopics(List<String> topics);

        void showPaperDOI(String DOI);

        void showPaperDate(String date);

        void showPaperImage(int resImageId);

        void showComments(List<Comment> comments);
    }
}
