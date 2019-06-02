package com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.following;

import com.unibs.zanotti.inforinvestigador.baseMVP.BaseContract;
import com.unibs.zanotti.inforinvestigador.domain.model.User;

import java.util.List;

public interface ListFollowingContract {
    interface View extends BaseContract.View {

        void showFollowingList(List<User> followingUsersList);
    }

    interface Presenter extends BaseContract.Presenter<View> {

    }
}
