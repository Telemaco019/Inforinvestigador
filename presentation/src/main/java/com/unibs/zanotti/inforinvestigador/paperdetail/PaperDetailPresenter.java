package com.unibs.zanotti.inforinvestigador.paperdetail;

import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.domain.IPaperService;
import com.unibs.zanotti.inforinvestigador.domain.model.Comment;
import com.unibs.zanotti.inforinvestigador.domain.model.Paper;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Listens to user action from the UI (e.g. {@link PaperDetailFragment}), retrieves the data and updates the UI as
 * required.
 */
public class PaperDetailPresenter implements PaperDetailContract.Presenter {
    private final PaperDetailContract.View mView;
    private final long paperId;
    private IPaperService paperService;

    public PaperDetailPresenter(long paperId, IPaperService paperService, PaperDetailContract.View mView) {
        this.mView = mView;
        this.paperId = paperId;
        this.paperService = paperService;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        openPaper();
    }

    private void openPaper() {
        Optional<Paper> optionalPaper = paperService.getPaper(paperId);
        if (optionalPaper.isPresent()) {
            Paper paper = optionalPaper.get();
            mView.showPaperTitle(paper.getPaperTitle());
            mView.showPaperCitations(paper.getPaperCitations());
            mView.showPaperAbstract(paper.getPaperAbstract());
            mView.showPaperDOI(paper.getPaperDoi());
            mView.showPaperPublisher(paper.getPaperPublisher());
            mView.showPaperDate(paper.getPaperDate());
            mView.showPaperAuthors(paper.getPaperAuthors());
            mView.showPaperTopics(paper.getPaperTopics());
            mView.showPaperImage(R.drawable.paper_preview_test); // TODO
            mView.showComments(paperService.getComments(paperId));
        }
    }

    @Override
    public void viewClicked(int viewId) {

    }

    @Override
    public void addComment(String comment) {
        // TODO: retrieve user info
        String author = "Test";
        Comment newComment = new Comment(comment,author,0,null,new ArrayList<>());
        // TODO: save comment to db

        mView.showNewComment(newComment);
        mView.clearCommentInputField();
    }
}
