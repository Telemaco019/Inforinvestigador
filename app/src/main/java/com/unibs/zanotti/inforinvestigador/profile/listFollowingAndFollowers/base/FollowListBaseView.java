package com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.base;

import com.unibs.zanotti.inforinvestigador.baseMVP.BaseFragment;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.ListFollowingAndFollowersActivity;
import com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.adapters.Listeners;

import java.util.List;

public abstract class FollowListBaseView extends BaseFragment<FollowListBaseContract.View, FollowListBaseContract.Presenter>
        implements FollowListBaseContract.View, Listeners.FollowersListListener {

    protected FollowListBaseRecyclerViewAdapter mAdapter;

    @Override
    public void onFollowButtonClicked(String userId, int adapterPosition) {
        presenter.onFollowButtonClicked(userId, adapterPosition);
    }

    @Override
    public void onFollowingButtonClicked(String userId, int adapterPosition) {
        presenter.onFollowingButtonClicked(userId, adapterPosition);
    }

    @Override
    public void onCardClicked(User user) {
        presenter.onCardClicked(user);
    }

    @Override
    public void showList(List<User> list) {
        mAdapter.setDataset(list);
    }

    @Override
    public void notifyItemUpdated(int adapterPosition) {
        mAdapter.notifyItemChanged(adapterPosition);
    }

    @Override
    public void setActivityResultFollowListUpdated() {
        getActivity().setResult(ListFollowingAndFollowersActivity.FOLLOWING_OR_FOLLOWERS_UPDATED_RC);
    }
}
