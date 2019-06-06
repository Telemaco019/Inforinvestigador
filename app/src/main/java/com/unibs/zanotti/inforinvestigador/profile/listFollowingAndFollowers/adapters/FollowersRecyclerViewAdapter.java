package com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.adapters;

import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.base.FollowListBaseRecyclerViewAdapter;

import java.util.List;

public class FollowersRecyclerViewAdapter extends FollowListBaseRecyclerViewAdapter {
    private static final String TAG = String.valueOf(FollowersRecyclerViewAdapter.class);

    public FollowersRecyclerViewAdapter(List<User> dataset,
                                        String modelUserId,
                                        IUserRepository userRepository,
                                        Listeners.FollowersListListener followersListListener) {
        super(dataset,modelUserId,userRepository,followersListListener);
    }
}
