package com.unibs.zanotti.inforinvestigador.paperdetail;

import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.data.IPaperRepository;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.model.Comment;
import com.unibs.zanotti.inforinvestigador.domain.model.Paper;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Listens to user action from the UI (e.g. {@link PaperDetailFragment}), retrieves the data and updates the UI as
 * required.
 */
public class PaperDetailPresenter implements PaperDetailContract.Presenter {
    private final PaperDetailContract.View mView;
    private IPaperRepository paperRepository;
    private IUserRepository userRepository;
    private String paperId;
    private CompositeDisposable disposables;

    public PaperDetailPresenter(String paperId,
                                IPaperRepository paperRepository,
                                IUserRepository userRepository,
                                PaperDetailContract.View mView) {
        this.mView = mView;
        this.paperRepository = paperRepository;
        this.userRepository = userRepository;
        this.paperId = paperId;
        disposables = new CompositeDisposable();

        mView.setPresenter(this);
    }

    @Override
    public void start() {
        openPaper();
    }

    @Override
    public void stop() {
        disposables.dispose();
    }

    private void openPaper() {
        Optional<Paper> optionalPaper = paperRepository.getPaper(paperId);
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
            mView.showComments(paper.getComments());
        }
        // TODO: manage case in which the paeper is null (e.g. data not available: show empty activity with message)
    }

    @Override
    public void viewClicked(int viewId) {

    }

    @Override
    public void addComment(String comment) {
        disposables.add(userRepository.getCurrentUser()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<Optional<User>>() {
                    @Override
                    public void onSuccess(Optional<User> user) {
                        if (user.isPresent()) {
                            Comment newComment = new Comment(
                                    comment,
                                    user.get().getName(),
                                    0,
                                    null,
                                    new ArrayList<>()
                            );
                            paperRepository.addComment(paperId, newComment);
                            mView.showNewComment(newComment);
                            mView.clearCommentInputField();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO
                    }
                }));
    }
}
