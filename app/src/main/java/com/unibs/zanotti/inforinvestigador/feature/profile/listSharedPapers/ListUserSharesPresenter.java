package com.unibs.zanotti.inforinvestigador.feature.profile.listSharedPapers;

import android.util.Log;
import com.google.common.collect.Lists;
import com.unibs.zanotti.inforinvestigador.baseMVP.BasePresenter;
import com.unibs.zanotti.inforinvestigador.data.IPaperRepository;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.model.FeedPaper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

public class ListUserSharesPresenter extends BasePresenter<ListUserSharesContract.View>
        implements ListUserSharesContract.Presenter {

    private static final String TAG = String.valueOf(ListUserSharesPresenter.class);

    private IPaperRepository paperRepository;
    private IUserRepository userRepository;

    private List<FeedPaper> sharedPapers;
    private String userId;

    public ListUserSharesPresenter(String userId, IPaperRepository paperRepository, IUserRepository userRepository) {
        this.userId = userId;
        this.paperRepository = paperRepository;
        this.userRepository = userRepository;
        sharedPapers = Lists.newArrayList();
    }

    @Override
    public void onPresenterCreated() {
        loadSharedPapers();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (sharedPapers != null && !sharedPapers.isEmpty()) {
            showSharedPapers();
        }
    }

    private void loadSharedPapers() {
        getView().showProgressBar();
        sharedPapers = Lists.newArrayList();
        disposables.add(paperRepository.getPapersSharedByUser(userId)
                .flatMap(paper -> userRepository.getUser(paper.getSharingUserId()).toObservable(),
                        (paper, user) -> new FeedPaper(paper.getPaperId(),
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
                .subscribeWith(new DisposableObserver<FeedPaper>() {
                    @Override
                    public void onNext(FeedPaper feedPaper) {
                        sharedPapers.add(feedPaper);
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

    private void showSharedPapers() {
        getView().hideProgressBar();
        getView().showContentLayout();
        getView().showSharedPapers(sharedPapers);
    }
}
