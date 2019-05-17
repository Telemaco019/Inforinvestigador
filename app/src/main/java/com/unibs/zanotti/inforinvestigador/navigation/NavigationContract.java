package com.unibs.zanotti.inforinvestigador.navigation;

import androidx.fragment.app.Fragment;
import com.unibs.zanotti.inforinvestigador.baseMVP.BaseContract;

public interface NavigationContract {
    interface View extends BaseContract.View {

        /**
         * Select the bottom navigation item associated to the fragment tag provided as argument
         *
         * @param fragmentTag
         */
        void selectBottomNavigationItem(String fragmentTag);

        /**
         * If already added to the activity, hide the fragment corresponding to the tag provided as argument
         * @param fragmentTag
         */
        void hideFragment(String fragmentTag);

        /**
         * Show the fragment corresponding to the tag provided as argument (it is assumed that that fragment has been
         * previously added to the activity)
         * @param fragmentTag
         */
        void showAddedFragment(String fragmentTag);

        /**
         * Add to the activity the fragment provided as first argument, using in the transaction the tag provided
         * as second argument
         * @param fragment
         * @param tag
         */
        void attachFragment(Fragment fragment, String tag);
    }

    interface Presenter extends BaseContract.Presenter<View> {

        boolean onBottomNavItemSelected(int bottomNavItemId);
    }
}
