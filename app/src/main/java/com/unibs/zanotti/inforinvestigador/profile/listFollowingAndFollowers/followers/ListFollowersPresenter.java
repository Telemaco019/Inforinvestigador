package com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.followers;

import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.base.FollowListBasePresenter;
import io.reactivex.observers.DisposableObserver;

import java.util.ArrayList;
import java.util.List;

public class ListFollowersPresenter extends FollowListBasePresenter implements ListFollowersContract.Presenter {

    private List<User> followersList;

    public ListFollowersPresenter(IUserRepository userRepository, User modelUser) {
        super(userRepository, modelUser);
    }

    @Override
    public void onPresenterCreated() {
        followersList = new ArrayList<>();
        disposables.add(userRepository.getFollowersUsersIds(modelUser.getId())
                .flatMapMaybe(userRepository::getUser)
                .subscribeWith(new DisposableObserver<User>() {
                    @Override
                    public void onNext(User user) {
                        followersList.add(user);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        showFollowersList();
                    }
                }));
    }

    private void showFollowersList() {
        getView().showList(followersList);
    }

    @Override
    public void onStart() {
        super.onStart();
        showFollowersList();
    }
}
