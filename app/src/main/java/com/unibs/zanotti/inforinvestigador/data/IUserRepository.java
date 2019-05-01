package com.unibs.zanotti.inforinvestigador.data;


import com.unibs.zanotti.inforinvestigador.domain.model.User;
import io.reactivex.Maybe;

public interface IUserRepository {

    Maybe<User> getUser(String userId);

    void updateUser(User user);

    /**
     * Return the user currently logged in Invorinvestigador
     */
    Maybe<User> getCurrentUser();
}
