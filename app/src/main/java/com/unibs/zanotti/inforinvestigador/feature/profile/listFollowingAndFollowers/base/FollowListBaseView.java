package com.unibs.zanotti.inforinvestigador.feature.profile.listFollowingAndFollowers.base;

import android.content.Intent;
import com.unibs.zanotti.inforinvestigador.baseMVP.BaseFragment;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import com.unibs.zanotti.inforinvestigador.feature.profile.ProfileActivity;
import com.unibs.zanotti.inforinvestigador.feature.profile.listFollowingAndFollowers.ListFollowingAndFollowersActivity;
import com.unibs.zanotti.inforinvestigador.feature.profile.listFollowingAndFollowers.adapters.Listeners;
import com.unibs.zanotti.inforinvestigador.utils.Actions;

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
        Intent intent = new Intent(Actions.SHOW_RESEARCHER_PROFILE);
        intent.putExtra(ProfileActivity.STRING_EXTRA_RESEARCHER_ID,user.getId());
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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

    public boolean isFollowListUpdated() {
        return presenter.isFollowListUpdated();
    }

    public void refreshData() {
        presenter.refreshData();
    }
}
