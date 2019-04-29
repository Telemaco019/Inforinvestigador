package com.unibs.zanotti.inforinvestigador.data;


import com.unibs.zanotti.inforinvestigador.domain.model.User;
import io.reactivex.Single;

import java.util.Optional;

public interface IUserRepository {

    Single<Optional<User>> getUser(String userId);

    void updateUser(User user);

    /**
     * Return the user currently logged in Invorinvestigador
     */
    Single<Optional<User>> getCurrentUser();
}
