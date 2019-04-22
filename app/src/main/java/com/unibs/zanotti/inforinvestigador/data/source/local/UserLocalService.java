package com.unibs.zanotti.inforinvestigador.data.source.local;

import com.unibs.zanotti.inforinvestigador.data.model.User;
import com.unibs.zanotti.inforinvestigador.data.source.IUserService;
import com.unibs.zanotti.inforinvestigador.data.source.local.dao.IUserLocalDao;

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
}
