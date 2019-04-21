package com.unibs.zanotti.inforinvestigador.paperdetail;

import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.data.model.Comment;
import com.unibs.zanotti.inforinvestigador.data.model.Paper;
import com.unibs.zanotti.inforinvestigador.data.source.IPaperDatasource;

import java.util.List;
import java.util.Optional;

/**
 * Listens to user action from the UI (e.g. {@link PaperDetailFragment}), retrieves the data and updates the UI as
 * required.
 */
public class PaperDetailPresenter implements PaperDetailContract.Presenter {
    private final PaperDetailContract.View mView;
    private final long paperId;
    private final List<Comment> comments;
    private IPaperDatasource paperDatasource;

    /**
     * @param paperId
     * @param mView
     * @param comments List of comments (made by the users) associated to the paper to show. Note: the comments
     *                 are show together with all the paper details, but they are associated to the share of the
     *                 paper and not to the paper itself.
     */
    public PaperDetailPresenter(long paperId, IPaperDatasource paperDatasource, PaperDetailContract.View mView,
                                List<Comment> comments) {
        this.mView = mView;
        this.paperId = paperId;
        this.comments = comments;
        this.paperDatasource = paperDatasource;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        openPaper();
    }

    private void openPaper() {
        Optional<Paper> optionalPaper = paperDatasource.getPaper(paperId);
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
            mView.showComments(comments);
        }
    }

}
