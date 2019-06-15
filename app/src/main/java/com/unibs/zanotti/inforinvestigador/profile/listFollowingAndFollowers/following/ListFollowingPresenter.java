package com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.following;

import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.base.FollowListBasePresenter;
import io.reactivex.observers.DisposableObserver;

import java.util.ArrayList;

public class ListFollowingPresenter extends FollowListBasePresenter implements ListFollowingContract.Presenter {

    public ListFollowingPresenter(IUserRepository userRepository, User user) {
        super(userRepository, user);
    }

    @Override
    protected void loadList() {
        followList = new ArrayList<>();
        disposables.add(userRepository.getFollowingUsersIds(modelUser.getId())
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
