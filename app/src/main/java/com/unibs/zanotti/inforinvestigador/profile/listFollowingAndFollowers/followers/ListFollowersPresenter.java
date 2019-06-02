package com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.followers;

import com.unibs.zanotti.inforinvestigador.baseMVP.BasePresenter;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import io.reactivex.observers.DisposableObserver;

import java.util.ArrayList;
import java.util.List;

public class ListFollowersPresenter extends BasePresenter<ListFollowersContract.View>
        implements ListFollowersContract.Presenter {

    private final String userId;
    private final IUserRepository userRepository;
    private List<User> followersList;

    public ListFollowersPresenter(IUserRepository userRepository, String userId) {
        this.userRepository = userRepository;
        this.userId = userId;
    }

    @Override
    public void onPresenterCreated() {
        followersList = new ArrayList<>();
        disposables.add(userRepository.getFollowersUsersIds(userId)
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
        getView().showFollowersList(followersList);
    }

    @Override
    public void onStart() {
        super.onStart();
        showFollowersList();
    }
}
