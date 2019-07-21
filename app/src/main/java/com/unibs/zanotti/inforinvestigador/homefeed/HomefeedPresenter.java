package com.unibs.zanotti.inforinvestigador.homefeed;

import android.os.Bundle;
import android.util.Log;
import com.unibs.zanotti.inforinvestigador.baseMVP.BasePresenter;
import com.unibs.zanotti.inforinvestigador.data.IPaperRepository;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.model.FeedPaper;
import com.unibs.zanotti.inforinvestigador.domain.model.ResearcherSuggestion;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomefeedPresenter extends BasePresenter<HomefeedContract.View> implements HomefeedContract.Presenter {
    private static final String KEY_RESEARCHERS_LIST_SAVED_POSITION = "HomefeedPresenter.RESEARCHERS_LIST_POSITION";
    private static final String KEY_PAPERS_LIST_SAVED_POSITION = "HomefeedPresenter.PAPERS_LIST_POSITION";

    private final IUserRepository userRepository;
    private IPaperRepository paperRepository;
    private List<FeedPaper> papersFeed;
    private List<ResearcherSuggestion> researchersFeed;

    /**
     * Flag that is true while the researcher suggestions are being loaded from the data layer (e.g. when the method
     * {@link HomefeedPresenter#loadingResearcherSuggestions} is being executed) and false all the other times
     */
    boolean loadingResearcherSuggestions = false;
    /**
     * Flag that is true while the paper shares are being loaded from the data layer (e.g when the method
     * {@link HomefeedPresenter#loadingPapersShares} is being executed) and false all the other times
     */
    boolean loadingPapersShares = false;

    public HomefeedPresenter(IPaperRepository paperRepository, IUserRepository userRepository) {
        this.paperRepository = paperRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void paperShareClicked(String paperId) {
        getView().showPaper(paperId);
    }

    @Override
    public void researcherSuggestionClicked(String researcherId) {
        getView().showResearcherProfile(researcherId);
    }

    @Override
    public void onRefresh() {
        getView().showLoadingProgressBar();
        loadPaperShares();
        loadResearchersSuggestions();
    }

    private void loadResearchersSuggestions() {
        loadingResearcherSuggestions = true;
        researchersFeed = new ArrayList<>();
        disposables.add(userRepository.getResearchersSuggestions(userRepository.getCurrentUserId()).subscribeWith(new DisposableObserver<ResearcherSuggestion>() {
            @Override
            public void onNext(ResearcherSuggestion researcherSuggestion) {
                researchersFeed.add(researcherSuggestion);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Collections.shuffle(researchersFeed);
                showResearchersFeed();
                loadingResearcherSuggestions = false;
                if (!loadingPapersShares) {
                    getView().hideLoadingProgressBar();
                    getView().showContentLayout();
                }
            }
        }));
    }

    private void loadPaperShares() {
        loadingPapersShares = true;
        papersFeed = new ArrayList<>();
        disposables.add(paperRepository.getPapers()
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
                        Log.e("***", "On next");
                        papersFeed.add(feedPaper);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("***", "On error" + e);

                    }

                    @Override
                    public void onComplete() {
                        Log.e("***", "On complete");
                        showPapersFeed();
                        loadingPapersShares = false;
                        if (!loadingResearcherSuggestions) {
                            getView().hideLoadingProgressBar();
                            getView().showContentLayout();
                        }
                    }
                })
        );
    }

    @Override
    public void onPresenterCreated() {
        getView().showLoadingProgressBar();
        loadPaperShares();
        loadResearchersSuggestions();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!loadingPapersShares && !loadingResearcherSuggestions) {
            getView().showContentLayout();
            showResearchersFeed();
            showPapersFeed();
            restoreScrollPositions();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        saveScrollPositions();
    }

    private void restoreScrollPositions() {
        Bundle stateBundle = getStateBundle();
        HomefeedContract.View view = getView();
        view.setScrollPositionPapersList(stateBundle.getInt(KEY_PAPERS_LIST_SAVED_POSITION));
        view.setScrollPositionResearchersList(stateBundle.getInt(KEY_RESEARCHERS_LIST_SAVED_POSITION));
    }

    private void saveScrollPositions() {
        Bundle stateBundle = getStateBundle();
        HomefeedContract.View view = getView();
        stateBundle.putInt(KEY_PAPERS_LIST_SAVED_POSITION, view.getScrollPositionPapersList());
        stateBundle.putInt(KEY_RESEARCHERS_LIST_SAVED_POSITION, view.getScrollPositionResearchersList());
    }

    private void showPapersFeed() {
        getView().showPapersFeed(papersFeed);
    }

    private void showResearchersFeed() {
        getView().showResearchersSuggestions(researchersFeed);
    }
}
