package com.unibs.zanotti.inforinvestigador.domain;


import com.unibs.zanotti.inforinvestigador.domain.model.User;

import java.util.Optional;

public interface IUserService {

    Optional<User> getUser(Long userId);
}