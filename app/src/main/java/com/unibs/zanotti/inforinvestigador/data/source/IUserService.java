package com.unibs.zanotti.inforinvestigador.data.source;

import com.unibs.zanotti.inforinvestigador.data.model.User;

import java.util.Optional;

public interface IUserService {

    Optional<User> getUser(Long userId);
}
