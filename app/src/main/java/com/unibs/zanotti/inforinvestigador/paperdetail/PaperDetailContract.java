package com.unibs.zanotti.inforinvestigador.paperdetail;

import com.unibs.zanotti.inforinvestigador.BasePresenter;
import com.unibs.zanotti.inforinvestigador.BaseView;
import com.unibs.zanotti.inforinvestigador.domain.model.Comment;

import java.util.List;

public interface PaperDetailContract {
    interface Presenter extends BasePresenter {

        void stop();

        void viewClicked(int viewId);

        void addComment(String comment);
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

        void showNewComment(Comment newComment);

        void clearCommentInputField();

        void scrollViewToBottom();
    }
}
