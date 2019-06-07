package com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.base;

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

    public FollowListBasePresenter(IUserRepository userRepository, User modelUser) {
        this.modelUser = modelUser;
        this.userRepository = userRepository;
        followList = new ArrayList<>();
    }

    @Override
    public void onFollowButtonClicked(String userId, int adapterPosition) {
        disposables.add(userRepository.followUser(modelUser.getId(), userId)
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
        disposables.add(userRepository.unfollowUser(modelUser.getId(), userId)
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
    public void onCardClicked(User user) {

    }

    @Override
    public void onPresenterCreated() {

    }
}
