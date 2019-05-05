package com.unibs.zanotti.inforinvestigador.data;


import com.unibs.zanotti.inforinvestigador.domain.model.User;
import io.reactivex.Completable;
import io.reactivex.Maybe;

public interface IUserRepository {

    Maybe<User> getUser(String userId);

    Completable saveUpdateUser(User user);

    /**
     * Return the id of the user currently logged in Invorinvestigador
     */
    String getCurrentUserId();
}
