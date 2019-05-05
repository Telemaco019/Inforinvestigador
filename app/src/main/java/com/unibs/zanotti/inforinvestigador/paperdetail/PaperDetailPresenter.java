package com.unibs.zanotti.inforinvestigador.paperdetail;

import android.util.Log;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.data.IPaperRepository;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.ModelFactory;
import com.unibs.zanotti.inforinvestigador.domain.model.Comment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void viewClicked(int viewId) {

    }

    @Override
    public void addComment(String comment) {
        String currentUserId = userRepository.getCurrentUserId();
        disposables.add(userRepository.getUser(currentUserId)
                .flatMapSingle(user -> paperRepository.saveUpdateComment(ModelFactory.createComment(paperId, comment, user.getName())))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Comment>() {
                    @Override
                    public void onSuccess(Comment comment) {
                        mView.showNewComment(comment);
                        mView.clearCommentInputField();
                        mView.scrollViewToBottom();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: update view properly
                        Log.e("**********************", "");
                    }
                })
        );
    }

    private void openPaper() {
        List<Comment> comments = new ArrayList<>();
        disposables.add(paperRepository.getPaper(paperId)
                .flatMapObservable(paper -> {
                    mView.showPaperTitle(paper.getPaperTitle());
                    mView.showPaperCitations(paper.getPaperCitations());
                    mView.showPaperAbstract(paper.getPaperAbstract());
                    mView.showPaperDOI(paper.getPaperDoi());
                    mView.showPaperPublisher(paper.getPaperPublisher());
                    mView.showPaperDate(paper.getPaperDate());
                    mView.showPaperAuthors(paper.getPaperAuthors());
                    mView.showPaperTopics(paper.getPaperTopics());
                    mView.showPaperImage(R.drawable.paper_preview_test);
                    return paperRepository.getComments(paperId);
                })
                .subscribeWith(new DisposableObserver<Comment>() {
                    @Override
                    public void onNext(Comment comment) {
                        comments.add(comment);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        mView.showComments(comments);
                    }
                }));
    }
}
