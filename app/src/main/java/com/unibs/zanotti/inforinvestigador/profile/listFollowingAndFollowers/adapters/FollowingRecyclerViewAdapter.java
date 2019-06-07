package com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.adapters;

import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.base.FollowListBaseRecyclerViewAdapter;

import java.util.List;

public class FollowingRecyclerViewAdapter extends FollowListBaseRecyclerViewAdapter {
    public FollowingRecyclerViewAdapter(List<User> dataset, String modelUserId, IUserRepository userRepository, Listeners.FollowersListListener followersListListener) {
        super(dataset, modelUserId, userRepository, followersListListener);
    }
}
