package com.unibs.zanotti.inforinvestigador.homefeed;

import android.util.Log;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.baseMVP.BasePresenter;
import com.unibs.zanotti.inforinvestigador.data.IPaperRepository;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.model.FeedPaper;
import com.unibs.zanotti.inforinvestigador.domain.model.ResearcherSuggestion;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class HomefeedPresenter extends BasePresenter<HomefeedContract.View> implements HomefeedContract.Presenter {
    private final IUserRepository userRepository;
    private IPaperRepository paperRepository;
    private List<FeedPaper> papersFeed;
    private List<ResearcherSuggestion> researchersFeed;

    public HomefeedPresenter(IPaperRepository paperRepository, IUserRepository userRepository) {
        this.paperRepository = paperRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void loadFeed() {
        loadPaperShares();
        loadResearchersSuggestions();
    }

    @Override
    public void paperShareClicked(String paperId) {
        getView().showPaperDetails(paperId);
    }

    private void loadResearchersSuggestions() {
        researchersFeed = computeResearcherSuggestions(1);
        showResearchersFeed();
    }

    /**
     * Computes the suggestions for the user with the id provided as parameter
     *
     * @param userId
     * @return
     */
    private List<ResearcherSuggestion> computeResearcherSuggestions(int userId) {
        // Dummy implementation

        List<ResearcherSuggestion> result = new ArrayList<>();

        result.add(
                new ResearcherSuggestion(
                        R.drawable.test_researcher_1,
                        "Maria Piras"
                )
        );
        result.add(
                new ResearcherSuggestion(
                        R.drawable.test_researcher_2,
                        "Leonor Freitas"
                )
        );
        result.add(
                new ResearcherSuggestion(
                        R.drawable.test_researcher_3,
                        "Antonio Lopes"
                )
        );
        result.add(
                new ResearcherSuggestion(
                        R.drawable.test_researcher_4,
                        "Cristiano Carvalho"
                )
        );
        result.add(
                new ResearcherSuggestion(
                        R.drawable.test_researcher_5,
                        "Teresa Sardinha"
                )
        );
        result.add(
                new ResearcherSuggestion(
                        R.drawable.test_researcher_6,
                        "Joana de Carvalho"
                )
        );
        result.add(
                new ResearcherSuggestion(
                        R.drawable.test_researcher_7,
                        "Mario Relha"
                )
        );

        return result;
    }

    private void loadPaperShares() {
        papersFeed = new ArrayList<>();

        disposables.add(paperRepository.getPapers()
                .flatMap(paper -> userRepository.getUser(paper.getSharingUserId()).toObservable(),
                        (paper, user) -> new FeedPaper(paper.getPaperId(),
                                paper.getPaperTitle(),
                                paper.getSharingUserComment(),
                                user.getName(),
                                user.getProfilePictureUri(), // FIXME
                                paper.getPaperDate(),
                                paper.getPaperTopics(),
                                paper.getPaperAuthors()))
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
                    }
                })
        );
    }

    @Override
    public void onPresenterCreated() {
        this.loadFeed();
    }

    @Override
    public void onStart() {
        this.showPapersFeed();
        this.showResearchersFeed();
    }

    private void showPapersFeed() {
        getView().showPapersFeed(papersFeed);
    }

    private void showResearchersFeed() {
        getView().showResearchersSuggestions(researchersFeed);
    }


}
