package com.unibs.zanotti.inforinvestigador.suggestions;

import com.unibs.zanotti.inforinvestigador.BasePresenter;
import com.unibs.zanotti.inforinvestigador.BaseView;
import com.unibs.zanotti.inforinvestigador.data.model.PaperShare;
import com.unibs.zanotti.inforinvestigador.data.model.ResearcherSuggestion;

import java.util.List;

public interface SuggesitonsContract {
    interface Presenter extends BasePresenter {
        void loadSuggestions();
    }

    interface View extends BaseView<Presenter> {
        void showPapersSuggestions(List<PaperShare> suggestions);

        void showResearchersSuggestions(List<ResearcherSuggestion> suggestions);
    }
}
