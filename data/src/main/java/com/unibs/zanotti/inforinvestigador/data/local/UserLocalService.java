package com.unibs.zanotti.inforinvestigador.data.local;

import com.unibs.zanotti.inforinvestigador.data.local.dao.IUserLocalDao;
import com.unibs.zanotti.inforinvestigador.domain.IUserService;
import com.unibs.zanotti.inforinvestigador.domain.model.User;

import java.util.Optional;

public class UserLocalService implements IUserService {
    private static volatile UserLocalService INSTANCE = null;

    private IUserLocalDao userLocalDao;

    public UserLocalService(IUserLocalDao userLocalDao) {
        this.userLocalDao = userLocalDao;
    }

    public static UserLocalService getInstance(IUserLocalDao userLocalDao) {
        if (INSTANCE == null) {
            INSTANCE = new UserLocalService(userLocalDao);
        }
        return INSTANCE;
    }

    @Override
    public Optional<User> getUser(Long userId) {
        return userLocalDao.get(userId);
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public User getCurrentUser(User user) {
        return null;
    }
}
