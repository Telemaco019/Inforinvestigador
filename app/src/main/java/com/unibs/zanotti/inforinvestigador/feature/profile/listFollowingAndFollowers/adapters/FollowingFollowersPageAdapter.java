package com.unibs.zanotti.inforinvestigador.feature.profile.listFollowingAndFollowers.adapters;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.unibs.zanotti.inforinvestigador.domain.model.User;

public class FollowingFollowersPageAdapter extends FragmentPagerAdapter {
    private static final int NUMBER_OF_FRAGMENT_TABS = 2;
    public static final int POSITION_TAB_FOLLOWERS_LIST = 0;
    public static final int POSITION_TAB_FOLLOWING_LIST = 1;
    public static final String TITLE_TAB_FOLLOWING_LIST = "Following";
    public static final String TITLE_TAB_FOLLOWERS_LIST = "Followers";

    private final User user;
    private Fragment[] fragments;
    private String[] fragmentsTitles;

    public FollowingFollowersPageAdapter(FragmentManager fm, User user, Fragment[] fragments, String[] fragmentsTitles) {
        super(fm);
        this.user = user;
        this.fragments = fragments;
        this.fragmentsTitles = fragmentsTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return NUMBER_OF_FRAGMENT_TABS;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentsTitles[position];
    }
}
