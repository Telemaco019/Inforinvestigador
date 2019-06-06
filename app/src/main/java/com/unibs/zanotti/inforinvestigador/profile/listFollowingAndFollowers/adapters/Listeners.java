package com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.adapters;

import com.unibs.zanotti.inforinvestigador.domain.model.User;

public interface Listeners {
    interface FollowersListListener {
        void onFollowButtonClicked(String userId, int adapterPosition);

        void onFollowingButtonClicked(String userId, int adapterPosition);

        void onCardClicked(User user);
    }
}
