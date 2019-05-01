package com.unibs.zanotti.inforinvestigador.data.local;

import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.data.local.dao.IUserLocalDao;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import io.reactivex.Maybe;

public class UserLocalRepository implements IUserRepository {
    private static volatile UserLocalRepository INSTANCE = null;

    private IUserLocalDao userLocalDao;

    public UserLocalRepository(IUserLocalDao userLocalDao) {
        this.userLocalDao = userLocalDao;
    }

    public static UserLocalRepository getInstance(IUserLocalDao userLocalDao) {
        if (INSTANCE == null) {
            INSTANCE = new UserLocalRepository(userLocalDao);
        }
        return INSTANCE;
    }

    @Override
    public Maybe<User> getUser(String userId) {
        return null;
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public Maybe<User> getCurrentUser() {
        return null;
    }
}
