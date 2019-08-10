package com.unibs.zanotti.inforinvestigador.feature.paperdetail;

import android.net.Uri;
import android.util.Log;
import com.unibs.zanotti.inforinvestigador.baseMVP.BasePresenter;
import com.unibs.zanotti.inforinvestigador.data.IPaperRepository;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.ModelFactory;
import com.unibs.zanotti.inforinvestigador.domain.model.Comment;
import com.unibs.zanotti.inforinvestigador.domain.model.Paper;
import com.unibs.zanotti.inforinvestigador.domain.utils.StringUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

/**
 * Listens to user action from the UI (e.g. {@link PaperDetailFragment}), retrieves the data and updates the UI as
 * required.
 */
public class PaperDetailPresenter extends BasePresenter<PaperDetailContract.View> implements PaperDetailContract.Presenter {
    private IPaperRepository paperRepository;
    private IUserRepository userRepository;
    private String paperId;
    /**
     * Paper loaded from the db and currently being displayed in the view
     */
    private Paper loadedPaper;

    public PaperDetailPresenter(String paperId,
                                IPaperRepository paperRepository,
                                IUserRepository userRepository) {
        this.paperRepository = paperRepository;
        this.userRepository = userRepository;
        this.paperId = paperId;
    }

    @Override
    public void commentLikeClicked(Comment comment) {
        String currentUserId = userRepository.getCurrentUserId();
        if (!comment.isLikedByCurrentUser()) {
            comment.setLikedByCurrentUser(true);
            getView().showComment(comment);
            disposables.add(paperRepository.likeComment(paperId, comment.getId(), currentUserId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableCompletableObserver() {
                        @Override
                        public void onComplete() {
                            // NO OP (Comment likes number gets automatically updated through real time data)
                        }

                        @Override
                        public void onError(Throwable e) {
                            // NO OP
                        }
                    }));
        } else {
            comment.setLikedByCurrentUser(false);
            getView().showComment(comment);
            disposables.add(paperRepository.unlikeComment(paperId, comment.getId(), currentUserId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableCompletableObserver() {
                        @Override
                        public void onComplete() {
                            // NO OP
                        }

                        @Override
                        public void onError(Throwable e) {
                            // NO OP
                        }
                    }));
        }
    }

    @Override
    public void addComment(String comment) {
        String currentUserId = userRepository.getCurrentUserId();
        disposables.add(userRepository.getUser(currentUserId)
                .flatMapSingle(user -> paperRepository.saveComment(ModelFactory.createComment(paperId, comment, user.getName(), currentUserId)))
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

    @Override
    public void gotoSourceButtonClicked() {
        getView().navigateToPaperSource(loadedPaper.getURL());
    }

    @Override
    public void addToLibraryClicked() {
        disposables.add(paperRepository.addPaperToLibrary(paperId, userRepository.getCurrentUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        getView().showMessagePaperAddedToLibrary();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showMessageCannotAddPaperToLibrary();
                    }
                }));
    }

    @Override
    public void onStart() {
        super.onStart();
        showLoadedPaper();
        loadPaperCommentsInRealTime();
    }

    /**
     * Display in the view, if not null (e.g if is has already been loaded), the
     * {@link PaperDetailPresenter#loadedPaper loaded paper}
     */
    private void showLoadedPaper() {
        if (loadedPaper != null) {
            getView().showPaperTitle(loadedPaper.getPaperTitle());
            getView().showPaperDOI(loadedPaper.getPaperDoi());
            if (loadedPaper.getPaperCitations() != null) {
                getView().showPaperCitations(loadedPaper.getPaperCitations());
            }
            if (StringUtils.isNotBlank(loadedPaper.getPaperAbstract())) {
                getView().showPaperAbstract(loadedPaper.getPaperAbstract());
            }
            if (StringUtils.isNotBlank(loadedPaper.getPaperPublisher())) {
                getView().showPaperPublisher(loadedPaper.getPaperPublisher());
            }
            if (StringUtils.isNotBlank(loadedPaper.getPaperDate())) {
                getView().showPaperDate(loadedPaper.getPaperDate());
            }
            if (loadedPaper.getPaperAuthors() != null && !loadedPaper.getPaperAuthors().isEmpty()) {
                getView().showPaperAuthors(loadedPaper.getPaperAuthors());
            }
            if (loadedPaper.getPaperTopics() != null && !loadedPaper.getPaperTopics().isEmpty()) {
                getView().showPaperTopics(loadedPaper.getPaperTopics());
            }
            if (StringUtils.isBlank(loadedPaper.getURL())) {
                getView().disableGotoSourceButton();
            }

            List<Uri> images = loadedPaper.getPaperImages();
            if (!images.isEmpty()) {
                getView().showPaperImages(images);
            }

            getView().showContentLayout();
        }
    }

    private void loadPaperCommentsInRealTime() {
        String currentUserId = userRepository.getCurrentUserId();
        disposables.add(paperRepository.getCommentsRealTime(paperId, currentUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Comment>() {
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

    private void loadPaper() {
        disposables.add(paperRepository.getPaper(paperId).subscribeWith(new DisposableMaybeObserver<Paper>() {
            @Override
            public void onSuccess(Paper paper) {
                loadedPaper = paper;
                showLoadedPaper();
                getView().hideProgressBar();
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
