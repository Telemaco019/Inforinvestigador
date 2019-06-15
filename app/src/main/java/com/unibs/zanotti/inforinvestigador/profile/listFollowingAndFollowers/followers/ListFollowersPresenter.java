package com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.followers;

import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.base.FollowListBasePresenter;
import io.reactivex.observers.DisposableObserver;

import java.util.ArrayList;

public class ListFollowersPresenter extends FollowListBasePresenter implements ListFollowersContract.Presenter {

    public ListFollowersPresenter(IUserRepository userRepository, User modelUser) {
        super(userRepository, modelUser);
    }

    @Override
    public void onPresenterCreated() {
        loadList();
    }

    @Override
    protected void loadList() {
        followList = new ArrayList<>();
        disposables.add(userRepository.getFollowersUsersIds(modelUser.getId())
                .flatMapMaybe(userRepository::getUser)
                .subscribeWith(new DisposableObserver<User>() {
                    @Override
                    public void onNext(User user) {
                        followList.add(user);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        showList();
                    }
                }));
    }
}
