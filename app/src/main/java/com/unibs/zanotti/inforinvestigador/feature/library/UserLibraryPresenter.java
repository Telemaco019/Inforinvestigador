package com.unibs.zanotti.inforinvestigador.feature.library;

import android.util.Log;
import com.google.common.collect.Lists;
import com.unibs.zanotti.inforinvestigador.baseMVP.BasePresenter;
import com.unibs.zanotti.inforinvestigador.data.IPaperRepository;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.model.PaperShare;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

public class UserLibraryPresenter
        extends BasePresenter<UserLibraryContract.View>
        implements UserLibraryContract.Presenter {
    private static final String TAG = String.valueOf(UserLibraryPresenter.class);

    private IPaperRepository paperRepository;
    private IUserRepository userRepository;
    private String userId;
    private List<PaperShare> libraryPapers;
    private Boolean isLoadingPapers;

    public UserLibraryPresenter(IPaperRepository paperRepository, IUserRepository userRepository, String userId) {
        this.paperRepository = paperRepository;
        this.userRepository = userRepository;
        this.userId = userId;
        this.isLoadingPapers = Boolean.FALSE;
    }

    @Override
    public void onPresenterCreated() {
       // NO OP
    }


    private void showEmptyLibraryMessage() {
        getView().hideProgressBar();
        getView().showContentLayout();
        getView().showEmptyLibraryMessage();
    }

    private void showSharedPapers() {
        getView().hideProgressBar();
        getView().hideEmptyLibraryMessage();
        getView().showContentLayout();
        getView().showLibraryPapers(libraryPapers);
    }

    @Override
    public void onStop() {
        super.onStop();
        isLoadingPapers = Boolean.FALSE;
    }

    @Override
    public void loadLibraryPapers() {
        if(isLoadingPapers) return;

        getView().showProgressBar();
        isLoadingPapers = Boolean.TRUE;
        libraryPapers = Lists.newArrayList();
        disposables.add(paperRepository.getLibraryPaperIds(userId)
                .map(id -> paperRepository.getPaper(id))
                .flatMap(Maybe::toObservable)
                .flatMap(paper -> userRepository.getUser(paper.getSharingUserId()).toObservable(),
                        (paper, user) -> new PaperShare(paper.getPaperId(),
                                paper.getPaperTitle(),
                                paper.getSharingUserComment(),
                                user.getName(),
                                user.getProfilePictureUri(),
                                paper.getPaperDate(),
                                paper.getPaperTopics(),
                                paper.getPaperAuthors(),
                                paper.getSharingUserId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<PaperShare>() {
                    @Override
                    public void onNext(PaperShare paperShare) {
                        libraryPapers.add(paperShare);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage(), e);
                        isLoadingPapers = Boolean.FALSE;
                    }

                    @Override
                    public void onComplete() {
                        isLoadingPapers = Boolean.FALSE;
                        if (libraryPapers.isEmpty()) {
                            showEmptyLibraryMessage();
                        } else {
                            showSharedPapers();
                        }
                    }
                }));
    }

    @Override
    public void paperShareDismissed(String paperId) {
        disposables.add(paperRepository.removePaperFromLibrary(paperId, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage(), e);
                    }

                    @Override
                    public void onComplete() {
                        removePaper(libraryPapers, paperId);
                        if (libraryPapers.isEmpty()) {
                            getView().showEmptyLibraryMessage();
                        }
                    }
                }));
    }

    private void removePaper(List<PaperShare> list, String paperId) {
        List<PaperShare> toRemove = Lists.newArrayList();
        for (PaperShare paper : list) {
            if (paper.getPaperId().equals(paperId)) {
                toRemove.add(paper);
            }
        }
        toRemove.forEach(list::remove);
    }
}
