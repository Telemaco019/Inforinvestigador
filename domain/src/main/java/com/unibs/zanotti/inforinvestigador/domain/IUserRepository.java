package com.unibs.zanotti.inforinvestigador.domain;


import com.unibs.zanotti.inforinvestigador.domain.model.User;

import java.util.Optional;

public interface IUserRepository {

    Optional<User> getUser(String userId);

    void updateUser(User user);

    /**
     * Return the user currently logged in Invorinvestigador
     */
    User getCurrentUser();
}
