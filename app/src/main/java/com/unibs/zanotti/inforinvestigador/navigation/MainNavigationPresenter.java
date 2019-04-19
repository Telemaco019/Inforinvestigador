package com.unibs.zanotti.inforinvestigador.navigation;

import androidx.fragment.app.Fragment;
import com.unibs.zanotti.inforinvestigador.LibraryFragment;
import com.unibs.zanotti.inforinvestigador.ProfileFragment;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.recommendation.list.ListRecommendationsFragment;
import org.jetbrains.annotations.NotNull;

public class MainNavigationPresenter implements MainNavigationContract.Presenter {

    private MainNavigationContract.View navigationView;

    public MainNavigationPresenter(@NotNull MainNavigationContract.View navigationView) {
        this.navigationView = navigationView;
        navigationView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public boolean navigate(int fragmentId) {
        Fragment destinationFragment;
        switch (fragmentId) {
            case R.id.bottom_bar_action_home:
                destinationFragment = new ListRecommendationsFragment();
                break;
            case R.id.bottom_bar_action_library:
                destinationFragment = new LibraryFragment();
                break;
            case R.id.bottom_bar_action_profile:
            default:
                destinationFragment = new ProfileFragment();
        }
        navigationView.displayFragment(destinationFragment);
        return true;
    }
}
