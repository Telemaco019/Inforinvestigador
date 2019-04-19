package com.unibs.zanotti.inforinvestigador.suggestions;

import com.unibs.zanotti.inforinvestigador.BasePresenter;
import com.unibs.zanotti.inforinvestigador.BaseView;
import com.unibs.zanotti.inforinvestigador.data.PaperSuggestion;
import com.unibs.zanotti.inforinvestigador.data.ResearcherSuggestion;

import java.util.List;

public interface SuggesitonsContract {
    interface Presenter extends BasePresenter {
        void loadSuggestions();
    }

    interface View extends BaseView<Presenter> {
        void showPapersSuggestions(List<PaperSuggestion> suggestions);

        void showResearchersSuggestions(List<ResearcherSuggestion> suggestions);
    }
}
