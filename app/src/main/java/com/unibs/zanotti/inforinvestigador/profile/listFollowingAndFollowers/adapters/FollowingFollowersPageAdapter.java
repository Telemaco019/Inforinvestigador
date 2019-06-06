package com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.adapters;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.followers.ListFollowersFragment;
import com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.following.ListFollowingFragment;

public class FollowingFollowersPageAdapter extends FragmentPagerAdapter {
    private static final int NUMBER_OF_FRAGMENT_TABS = 2;
    public static final int POSITION_TAB_FOLLOWERS_LIST = 0;
    public static final int POSITION_TAB_FOLLOWING_LIST = 1;
    private static final CharSequence TITLE_TAB_FOLLOWING_LIST = "Following";
    private static final CharSequence TITLE_TAB_FOLLOWERS_LIST = "Followers";

    private final User user;

    public FollowingFollowersPageAdapter(FragmentManager fm, User user) {
        super(fm);
        this.user = user;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == POSITION_TAB_FOLLOWERS_LIST) {
            return ListFollowersFragment.newInstance(user);
        } else {
            return ListFollowingFragment.newInstance(user);
        }
    }

    @Override
    public int getCount() {
        return NUMBER_OF_FRAGMENT_TABS;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position== POSITION_TAB_FOLLOWERS_LIST) {
            return TITLE_TAB_FOLLOWERS_LIST;
        } else {
            return TITLE_TAB_FOLLOWING_LIST;
        }
    }
}
