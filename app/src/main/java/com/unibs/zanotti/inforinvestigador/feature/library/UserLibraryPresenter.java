package com.unibs.zanotti.inforinvestigador.feature.library;

import android.util.Log;
import com.google.common.collect.Lists;
import com.unibs.zanotti.inforinvestigador.baseMVP.BasePresenter;
import com.unibs.zanotti.inforinvestigador.data.IPaperRepository;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.model.PaperShare;
import io.reactivex.android.schedulers.AndroidSchedulers;
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

    public UserLibraryPresenter(IPaperRepository paperRepository, IUserRepository userRepository, String userId) {
        this.paperRepository = paperRepository;
        this.userRepository = userRepository;
        this.userId = userId;
    }

    @Override
    public void onPresenterCreated() {
        getView().showProgressBar();
        loadLibraryPapers();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (libraryPapers != null && !libraryPapers.isEmpty()) {
            showSharedPapers();
        }
    }

    private void showSharedPapers() {
        getView().hideProgressBar();
        getView().showContentLayout();
        getView().showLibraryPapers(libraryPapers);
    }

    private void loadLibraryPapers() {
        libraryPapers = Lists.newArrayList();
        disposables.add(paperRepository.getPapersSharedByUser(userId)
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
                    }

                    @Override
                    public void onComplete() {
                        showSharedPapers();
                    }
                }));
    }

}
