package com.unibs.zanotti.inforinvestigador.feature.paperdetail;

import android.net.Uri;
import com.unibs.zanotti.inforinvestigador.baseMVP.BaseContract;
import com.unibs.zanotti.inforinvestigador.domain.model.Comment;

import java.util.List;

public interface PaperDetailContract {
    interface Presenter extends BaseContract.Presenter<View> {
        void commentLikeClicked(Comment comment);

        void addComment(String comment);

        void gotoSourceButtonClicked();

        void addToLibraryClicked();
    }

    interface View extends BaseContract.View {
        void showPaperImages(List<Uri> paperImagesUriList);

        void showPaperTitle(String paperTitle);

        void showPaperAbstract(String paperAbstract);

        void showPaperAuthors(List<String> authors);

        void showPaperPublisher(String publisher);

        void showPaperCitations(int citations);

        void showPaperTopics(List<String> topics);

        void showPaperDOI(String DOI);

        void showPaperDate(String date);

        void clearCommentInputField();

        void scrollViewToFirstComment();

        /**
         * Show the comment provided as argument. If the view is already showing a comment with the same id as the
         * one provided as argument, then update that comment with the new one.
         * @param comment
         */
        void showComment(Comment comment);

        void hideProgressBar();

        void showContentLayout();

        void navigateToPaperSource(String url);

        void disableGotoSourceButton();

        void showMessagePaperAddedToLibrary();

        void showMessageCannotAddPaperToLibrary();
    }
}
