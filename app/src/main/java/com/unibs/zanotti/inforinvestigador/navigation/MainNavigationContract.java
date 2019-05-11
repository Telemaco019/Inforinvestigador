package com.unibs.zanotti.inforinvestigador.navigation;

import androidx.fragment.app.Fragment;
import com.unibs.zanotti.inforinvestigador.BasePresenter;
import com.unibs.zanotti.inforinvestigador.BaseView;

public interface MainNavigationContract {
    interface Presenter extends BasePresenter {
        boolean navigate(int fragmentId);
    }

    interface View extends BaseView<Presenter> {
        void displayFragment(int fragmentNumber);

        void addFragmentToAdapter(Fragment fragment, String title);
    }
}
