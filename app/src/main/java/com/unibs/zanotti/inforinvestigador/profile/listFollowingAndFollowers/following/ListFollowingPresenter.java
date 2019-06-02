package com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.following;

import com.unibs.zanotti.inforinvestigador.baseMVP.BasePresenter;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import io.reactivex.observers.DisposableObserver;

import java.util.ArrayList;
import java.util.List;

public class ListFollowingPresenter extends BasePresenter<ListFollowingContract.View>
        implements ListFollowingContract.Presenter {

    private final IUserRepository userRepository;
    private final String userId;
    private List<User> followingUsersList;

    public ListFollowingPresenter(IUserRepository userRepository, String userId) {
        this.userRepository = userRepository;
        this.userId = userId;
    }


    @Override
    public void onPresenterCreated() {
        followingUsersList = new ArrayList<>();
        disposables.add(userRepository.getFollowingUsersIds(userId)
                .flatMapMaybe(userRepository::getUser)
                .subscribeWith(new DisposableObserver<User>() {
                    @Override
                    public void onNext(User user) {
                        followingUsersList.add(user);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        showFollowingList();
                    }
                }));
    }

    private void showFollowingList() {
        getView().showFollowingList(followingUsersList);
    }

    @Override
    public void onStart() {
        super.onStart();
        showFollowingList();
    }
}
