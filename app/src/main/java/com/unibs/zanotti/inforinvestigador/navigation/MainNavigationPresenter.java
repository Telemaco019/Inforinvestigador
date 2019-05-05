package com.unibs.zanotti.inforinvestigador.navigation;

import androidx.fragment.app.Fragment;
import com.unibs.zanotti.inforinvestigador.LibraryFragment;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.utils.StringUtils;
import com.unibs.zanotti.inforinvestigador.homefeed.HomefeedFragment;
import com.unibs.zanotti.inforinvestigador.homefeed.HomefeedPresenter;
import com.unibs.zanotti.inforinvestigador.profile.ProfileFragment;
import com.unibs.zanotti.inforinvestigador.profile.ProfilePresenter;
import com.unibs.zanotti.inforinvestigador.utils.Injection;
import org.jetbrains.annotations.NotNull;

public class MainNavigationPresenter implements MainNavigationContract.Presenter {

    private final IUserRepository userRepository;
    private MainNavigationContract.View navigationView;

    public MainNavigationPresenter(@NotNull MainNavigationContract.View navigationView, IUserRepository userRepository) {
        this.navigationView = navigationView;
        this.userRepository = userRepository;
        navigationView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public boolean navigate(int fragmentId) {
        Fragment destinationFragment;
        switch (fragmentId) {
            case R.id.bottom_bar_action_home: {
                HomefeedFragment fragment = new HomefeedFragment();
                new HomefeedPresenter(fragment,
                        Injection.providePaperRepository(),
                        Injection.provideUserRepository());
                destinationFragment = fragment;
                break;
            }
            case R.id.bottom_bar_action_library:
                destinationFragment = new LibraryFragment();
                break;
            case R.id.bottom_bar_action_profile: {
                String currentUserId = userRepository.getCurrentUserId();
                if (StringUtils.isNotBlank(currentUserId)) {
                    ProfileFragment profileFragment = ProfileFragment.newInstance(currentUserId);
                    new ProfilePresenter(profileFragment, Injection.provideUserRepository(), currentUserId);
                    destinationFragment = profileFragment;
                } else {
                    destinationFragment = new MainNavigationErrorFragment();
                }
                break;
            }
            default:
                destinationFragment = new MainNavigationErrorFragment();
        }
        navigationView.displayFragment(destinationFragment);
        return true;
    }
}
