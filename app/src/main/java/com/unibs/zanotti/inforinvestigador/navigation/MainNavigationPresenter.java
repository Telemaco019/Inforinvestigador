package com.unibs.zanotti.inforinvestigador.navigation;

import androidx.fragment.app.Fragment;
import com.unibs.zanotti.inforinvestigador.LibraryFragment;
import com.unibs.zanotti.inforinvestigador.ProfileFragment;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.data.source.local.PaperLocalService;
import com.unibs.zanotti.inforinvestigador.data.source.local.UserLocalService;
import com.unibs.zanotti.inforinvestigador.data.source.local.dao.impl.DummyPaperLocalDao;
import com.unibs.zanotti.inforinvestigador.data.source.local.dao.impl.DummyUserLocalDao;
import com.unibs.zanotti.inforinvestigador.homefeed.HomefeedFragment;
import com.unibs.zanotti.inforinvestigador.homefeed.HomefeedPresenter;
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
            case R.id.bottom_bar_action_home: {
                HomefeedFragment fragment = new HomefeedFragment();
                new HomefeedPresenter(fragment,
                        PaperLocalService.getInstance(new DummyPaperLocalDao()),
                        UserLocalService.getInstance(new DummyUserLocalDao()));
                destinationFragment = fragment;
                break;
            }
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
