package com.unibs.zanotti.inforinvestigador.feature.navigation;

import androidx.fragment.app.Fragment;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.baseMVP.BasePresenter;
import com.unibs.zanotti.inforinvestigador.domain.utils.StringUtils;
import com.unibs.zanotti.inforinvestigador.feature.addPaper.AddPaperFragment;
import com.unibs.zanotti.inforinvestigador.feature.homefeed.HomefeedFragment;
import com.unibs.zanotti.inforinvestigador.feature.library.UserLibraryFragment;
import com.unibs.zanotti.inforinvestigador.feature.profile.ProfileFragment;
import com.unibs.zanotti.inforinvestigador.utils.Injection;

import java.util.HashMap;

public class NavigationPresenter extends BasePresenter<NavigationContract.View> implements NavigationContract.Presenter {
    private static final String SAVED_STATE_CURRENT_FRAGMENT_KEY = "NavigationPresenter.bundle.CURRENT_FRAGMENT_KEY";

    /**
     * Default fragment displayed when the activity is created and started for the first time (and thus the presenter's
     * bundle does not contain any value for the {@link NavigationPresenter#SAVED_STATE_CURRENT_FRAGMENT_KEY})
     */
    private static final String DEFAULT_ACTIVE_FRAGMENT_ON_START = MainNavigationActivity.TAG_FRAGMENT_HOMEFEED;

    /**
     * Fragments displayed in the main navigation container according to the bottom nav item selected
     */
    private HashMap<String, Fragment> fragments;
    /**
     * Tag of the fragment currently being displayed. Used to restore the navigation tab after configuration changes
     */
    private String activeFragmentTag;

    public NavigationPresenter() {
        fragments = new HashMap<>();
    }

    @Override
    public void onPresenterCreated() {
        // NO OP
    }

    @Override
    public boolean onBottomNavItemSelected(int bottomNavItemId) {
        switch (bottomNavItemId) {
            case R.id.bottom_bar_action_home: {
                if (fragments.get(MainNavigationActivity.TAG_FRAGMENT_HOMEFEED) == null) {
                    Fragment fragment = HomefeedFragment.newInstance();
                    fragments.put(MainNavigationActivity.TAG_FRAGMENT_HOMEFEED, fragment);
                    attachFragmentToView(fragment, MainNavigationActivity.TAG_FRAGMENT_HOMEFEED);
                }
                activeFragmentTag = MainNavigationActivity.TAG_FRAGMENT_HOMEFEED;
                updateActiveFragment();
                break;
            }
            case R.id.bottom_bar_action_profile: {
                if (fragments.get(MainNavigationActivity.TAG_FRAGMENT_PROFILE) == null) {
                    ProfileFragment fragment = ProfileFragment.newInstance(Injection.provideUserRepository().getCurrentUserId());
                    fragments.put(MainNavigationActivity.TAG_FRAGMENT_PROFILE, fragment);
                    attachFragmentToView(fragment, MainNavigationActivity.TAG_FRAGMENT_PROFILE);
                }
                activeFragmentTag = MainNavigationActivity.TAG_FRAGMENT_PROFILE;
                updateActiveFragment();
                break;
            }
            case R.id.bottom_bar_action_library: {
                if (fragments.get(MainNavigationActivity.TAG_FRAGMENT_LIBRARY) == null) {
                    Fragment fragment = UserLibraryFragment.newInstance(Injection.provideUserRepository().getCurrentUserId());
                    fragments.put(MainNavigationActivity.TAG_FRAGMENT_LIBRARY, fragment);
                    attachFragmentToView(fragment, MainNavigationActivity.TAG_FRAGMENT_LIBRARY);
                }
                activeFragmentTag = MainNavigationActivity.TAG_FRAGMENT_LIBRARY;
                updateActiveFragment();
                break;
            }
            case R.id.bottom_bar_action_add: {
                if (fragments.get(MainNavigationActivity.TAG_FRAGMENT_ADD_PAPER) == null) {
                    Fragment fragment = AddPaperFragment.newInstance();
                    fragments.put(MainNavigationActivity.TAG_FRAGMENT_ADD_PAPER, fragment);
                    attachFragmentToView(fragment, MainNavigationActivity.TAG_FRAGMENT_ADD_PAPER);
                }
                activeFragmentTag = MainNavigationActivity.TAG_FRAGMENT_ADD_PAPER;
                updateActiveFragment();
                break;
            }
        }

        return true;
    }

    /**
     * Update the active fragment by displaying it in the activity view. In order to do that, hide all the other
     * fragments added to the activity and show the active fragment.
     */
    private void updateActiveFragment() {
        fragments.keySet().forEach(tag -> {
            if (!tag.equals(activeFragmentTag)) {
                getView().hideFragment(tag);
            }
        });

        getView().showAddedFragment(activeFragmentTag);
    }

    @Override
    public void onStop() {
        super.onStop();
        getStateBundle().putString(SAVED_STATE_CURRENT_FRAGMENT_KEY, activeFragmentTag);
    }

    @Override
    public void onStart() {
        super.onStart();
        initializeActiveFragmentId();
        attachInstantiatedFragmentsToView();
        getView().selectBottomNavigationItem(activeFragmentTag);
    }

    /**
     * Attach to the view, if they are not already added, all the fragments of the array
     * {@link NavigationPresenter#fragments}, but without showing them.
     * <p>
     * This method is supposed to be used for adding to the view the fragments that have been previously instantiated
     * (and thus have already been attached to the view once), but that have been removed after an activity restart
     * due to a configuration change
     * </p>
     */
    private void attachInstantiatedFragmentsToView() {
        fragments.entrySet().forEach(entry -> {
            Fragment fragment = entry.getValue();
            String tag = entry.getKey();
            getView().attachFragment(fragment, tag);
            getView().hideFragment(tag);
        });
    }

    /**
     * Attach to the view, if it is not already added, the fragments provided as argument, but without showing it.
     *
     * @param fragment
     */
    private void attachFragmentToView(Fragment fragment, String tag) {
        getView().attachFragment(fragment, tag);
        getView().hideFragment(tag);
    }

    /**
     * Initialize the field {@link NavigationPresenter#activeFragmentTag} by either restoring it from the value
     * previously stored in the presenter's bundle or by setting it to a default
     * value
     */
    private void initializeActiveFragmentId() {
        String savedActiveFragmentTag = getStateBundle().getString(SAVED_STATE_CURRENT_FRAGMENT_KEY);
        if (StringUtils.isNotBlank(savedActiveFragmentTag)) {
            activeFragmentTag = savedActiveFragmentTag;
        } else {
            activeFragmentTag = DEFAULT_ACTIVE_FRAGMENT_ON_START;
        }
    }
}
