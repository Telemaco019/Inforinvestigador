package com.unibs.zanotti.inforinvestigador.homefeed;

import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.domain.IPaperRepository;
import com.unibs.zanotti.inforinvestigador.domain.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.model.FeedPaper;
import com.unibs.zanotti.inforinvestigador.domain.model.Paper;
import com.unibs.zanotti.inforinvestigador.domain.model.ResearcherSuggestion;
import com.unibs.zanotti.inforinvestigador.domain.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HomefeedPresenter implements HomefeedContract.Presenter {

    private final HomefeedContract.View view;
    private final IUserRepository userService;
    private IPaperRepository paperService;

    public HomefeedPresenter(HomefeedContract.View view,
                             IPaperRepository paperService,
                             IUserRepository userService) {
        this.view = view;
        this.paperService = paperService;
        this.userService = userService;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        loadFeed();
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
        for (Paper paper : paperService.getPapers()) {
            Optional<User> optionalUser = userService.getUser(paper.getSharingUserId());
            papersFeed.add(new FeedPaper(
                    paper.getPaperId(),
                    paper.getPaperTitle(),
                    paper.getSharingUserComment(),
                    optionalUser.map(User::getName).orElse(""),
                    optionalUser.map(u -> u.getProfilePictureUri().toString()).orElse(""), // FIXME
                    paper.getPaperDate(),
                    paper.getPaperTopics(),
                    paper.getPaperAuthors()
            ));
        }
        view.showPapersFeed(papersFeed);
    }
}
