package com.unibs.zanotti.inforinvestigador.homefeed;

import android.util.Log;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.data.IPaperRepository;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.model.FeedPaper;
import com.unibs.zanotti.inforinvestigador.domain.model.ResearcherSuggestion;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

import java.util.ArrayList;
import java.util.List;

public class HomefeedPresenter implements HomefeedContract.Presenter {

    private final HomefeedContract.View view;
    private final IUserRepository userRepository;
    private IPaperRepository paperRepository;
    private CompositeDisposable disposables;

    public HomefeedPresenter(HomefeedContract.View view,
                             IPaperRepository paperRepository,
                             IUserRepository userRepository) {
        this.view = view;
        this.paperRepository = paperRepository;
        this.userRepository = userRepository;
        disposables = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void start() {
        loadFeed();
    }

    @Override
    public void stop() {
        disposables.dispose();
    }

    @Override
    public void loadFeed() {
        loadPaperShares();
        loadResearchersSuggestions();
    }

    @Override
    public void paperShareClicked(String paperId) {
        view.showPaperDetails(paperId);
    }

    private void loadResearchersSuggestions() {
        List<ResearcherSuggestion> researchersSuggestions = computeResearcherSuggestions(1);
        view.showResearchersSuggestions(researchersSuggestions);
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
        List<FeedPaper> papersFeed = new ArrayList<>();

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
                .subscribeWith(new DisposableObserver<FeedPaper>() {
                    @Override
                    public void onNext(FeedPaper feedPaper) {
                        Log.e("***","On next");
                        papersFeed.add(feedPaper);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("***","On error" + e);

                    }

                    @Override
                    public void onComplete() {
                        Log.e("***","On complete");
                        view.showPapersFeed(papersFeed);
                    }
                })
        );
    }
}
