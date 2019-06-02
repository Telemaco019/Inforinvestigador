package com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.followers;

import com.unibs.zanotti.inforinvestigador.baseMVP.BaseContract;
import com.unibs.zanotti.inforinvestigador.domain.model.User;

import java.util.List;

public interface ListFollowersContract {
    interface View extends BaseContract.View {

        void showFollowersList(List<User> followersList);
    }

    interface Presenter extends BaseContract.Presenter<View> {

    }
}
