package com.unibs.zanotti.inforinvestigador.paperdetail;

import android.util.Log;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.baseMVP.BasePresenter;
import com.unibs.zanotti.inforinvestigador.data.IPaperRepository;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.ModelFactory;
import com.unibs.zanotti.inforinvestigador.domain.model.Comment;
import com.unibs.zanotti.inforinvestigador.domain.model.Paper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Listens to user action from the UI (e.g. {@link PaperDetailFragment}), retrieves the data and updates the UI as
 * required.
 */
public class PaperDetailPresenter extends BasePresenter<PaperDetailContract.View> implements PaperDetailContract.Presenter {
    private IPaperRepository paperRepository;
    private IUserRepository userRepository;
    private String paperId;

    public PaperDetailPresenter(String paperId,
                                IPaperRepository paperRepository,
                                IUserRepository userRepository) {
        this.paperRepository = paperRepository;
        this.userRepository = userRepository;
        this.paperId = paperId;
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
                        getView().clearCommentInputField();
                        getView().scrollViewToFirstComment();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: update view properly
                        Log.e("**********************", "");
                    }
                })
        );
    }

    private void loadPaper() {
        loadPaperInfo();
        loadPaperCommentsInRealTime();
    }

    private void loadPaperCommentsInRealTime() {
        disposables.add(paperRepository.getCommentsRealTime(paperId).subscribeWith(new DisposableObserver<Comment>() {
            @Override
            public void onNext(Comment comment) {
                getView().showComment(comment);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
            }
        }));
    }

    private void loadPaperInfo() {
        disposables.add(paperRepository.getPaper(paperId).subscribeWith(new DisposableMaybeObserver<Paper>() {
            @Override
            public void onSuccess(Paper paper) {
                getView().showPaperTitle(paper.getPaperTitle());
                getView().showPaperCitations(paper.getPaperCitations());
                getView().showPaperAbstract(paper.getPaperAbstract());
                getView().showPaperDOI(paper.getPaperDoi());
                getView().showPaperPublisher(paper.getPaperPublisher());
                getView().showPaperDate(paper.getPaperDate());
                getView().showPaperAuthors(paper.getPaperAuthors());
                getView().showPaperTopics(paper.getPaperTopics());
                getView().showPaperImage(R.drawable.paper_preview_test);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }));
    }

    @Override
    public void onPresenterCreated() {
        this.loadPaper();
    }
}
