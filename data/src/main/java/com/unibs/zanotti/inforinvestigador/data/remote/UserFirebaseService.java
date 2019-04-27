package com.unibs.zanotti.inforinvestigador.data.remote;

import com.unibs.zanotti.inforinvestigador.domain.IUserService;
import com.unibs.zanotti.inforinvestigador.domain.model.User;

import java.util.Optional;

public class UserFirebaseService implements IUserService {
    @Override
    public Optional<User> getUser(Long userId) {
        return Optional.empty();
    }
}
