package com.unibs.zanotti.inforinvestigador.feature.profile.listFollowingAndFollowers.base;

import android.util.Log;
import com.unibs.zanotti.inforinvestigador.baseMVP.BasePresenter;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

public abstract class FollowListBasePresenter extends BasePresenter<FollowListBaseContract.View>
        implements FollowListBaseContract.Presenter {

    private static final String TAG = String.valueOf(FollowListBasePresenter.class);

    protected final User modelUser;
    protected final IUserRepository userRepository;
    protected List<User> followList;
    /**
     * Indicates if followers/following list has been updated, e.g. the user started or stopped following another user
     * while viewing the list
     */
    protected boolean followListUpdated;

    public FollowListBasePresenter(IUserRepository userRepository, User modelUser) {
        this.modelUser = modelUser;
        this.userRepository = userRepository;
        followList = new ArrayList<>();
        followListUpdated = false;
    }

    @Override
    public void onFollowButtonClicked(String userId, int adapterPosition) {
        if (!followListUpdated) {
            followListUpdated = true;
            getView().setActivityResultFollowListUpdated();
        }

        disposables.add(userRepository.followUser(userRepository.getCurrentUserId(), userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        getView().notifyItemUpdated(adapterPosition);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error following user: ", e);
                    }
                }));
    }

    @Override
    public void onFollowingButtonClicked(String userId, int adapterPosition) {
        if (!followListUpdated) {
            followListUpdated = true;
            getView().setActivityResultFollowListUpdated();
        }

        disposables.add(userRepository.unfollowUser(userRepository.getCurrentUserId(), userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        getView().notifyItemUpdated(adapterPosition);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error unfollowing user: ", e);
                    }
                }));
    }

    @Override
    public void onPresenterCreated() {
        this.loadList();
    }

    @Override
    public void onStart() {
        super.onStart();
        this.showList();
    }

    @Override
    public void refreshData() {
        this.loadList();
        followListUpdated = false;
    }

    protected void showList() {
        getView().showList(followList);
    }

    public boolean isFollowListUpdated() {
        return followListUpdated;
    }

    protected abstract void loadList();
}
