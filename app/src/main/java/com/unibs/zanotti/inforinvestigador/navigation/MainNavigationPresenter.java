package com.unibs.zanotti.inforinvestigador.navigation;

import androidx.fragment.app.Fragment;
import com.unibs.zanotti.inforinvestigador.LibraryFragment;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.homefeed.HomefeedFragment;
import com.unibs.zanotti.inforinvestigador.homefeed.HomefeedPresenter;
import com.unibs.zanotti.inforinvestigador.profile.ProfileFragment;
import com.unibs.zanotti.inforinvestigador.profile.ProfilePresenter;
import com.unibs.zanotti.inforinvestigador.utils.Injection;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainNavigationPresenter implements MainNavigationContract.Presenter {

    private final IUserRepository userRepository;
    private MainNavigationContract.View navigationView;
    private Map<Integer, Fragment> fragmentsMap;
    private List<Fragment> fragmentList;

    public MainNavigationPresenter(@NotNull MainNavigationContract.View navigationView, IUserRepository userRepository) {
        this.navigationView = navigationView;
        this.userRepository = userRepository;
        navigationView.setPresenter(this);
        fragmentsMap = new HashMap<>();
        fragmentList = new ArrayList<>();
    }

    @Override
    public void start() {
        initializeFragments();
    }

    private void initializeFragments() {
        String currentUserId = userRepository.getCurrentUserId();
        HomefeedFragment homefeedFragment = new HomefeedFragment();
        ProfileFragment profileFragment = ProfileFragment.newInstance(currentUserId);
        LibraryFragment libraryFragment = new LibraryFragment();

        new HomefeedPresenter(homefeedFragment, Injection.providePaperRepository(), Injection.provideUserRepository());
        new ProfilePresenter(profileFragment, Injection.provideUserRepository(), currentUserId);

        fragmentList.add(homefeedFragment);
        navigationView.addFragmentToAdapter(homefeedFragment, "title 1");
        fragmentList.add(profileFragment);
        navigationView.addFragmentToAdapter(profileFragment, "title 2");
        fragmentList.add(libraryFragment);
        navigationView.addFragmentToAdapter(libraryFragment, "title 3");

        fragmentsMap.put(R.id.bottom_bar_action_home, homefeedFragment);
        fragmentsMap.put(R.id.bottom_bar_action_library, libraryFragment);
        fragmentsMap.put(R.id.bottom_bar_action_profile, profileFragment);
    }

    @Override
    public boolean navigate(int fragmentId) {
        if (fragmentsMap.containsKey(fragmentId)) {
            Fragment selectedFragment = fragmentsMap.get(fragmentId);
            navigationView.displayFragment(fragmentList.indexOf(selectedFragment));
        }

        return true;
    }
}
