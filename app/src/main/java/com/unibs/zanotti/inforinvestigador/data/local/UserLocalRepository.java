package com.unibs.zanotti.inforinvestigador.data.local;

import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.data.local.dao.IUserLocalDao;
import com.unibs.zanotti.inforinvestigador.domain.model.User;

import java.util.Optional;

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
    public Optional<User> getUser(String userId) {
        return userLocalDao.get(userId);
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public User getCurrentUser() {
        return null;
    }
}
