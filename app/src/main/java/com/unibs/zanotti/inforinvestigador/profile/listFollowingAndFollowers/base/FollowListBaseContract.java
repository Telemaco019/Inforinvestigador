package com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.base;

import com.unibs.zanotti.inforinvestigador.baseMVP.BaseContract;
import com.unibs.zanotti.inforinvestigador.domain.model.User;

import java.util.List;

public interface FollowListBaseContract {
    interface View extends BaseContract.View {

        void showList(List<User> list);

        void notifyItemUpdated(int adapterPosition);

        void setActivityResultFollowListUpdated();
    }

    interface Presenter extends BaseContract.Presenter<FollowListBaseContract.View> {

        void onFollowButtonClicked(String userId, int adapterPosition);

        void onFollowingButtonClicked(String userId, int adapterPosition);

        void onCardClicked(User user);
    }
}
