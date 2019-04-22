package com.unibs.zanotti.inforinvestigador.data.source.local.dao.impl;

import com.unibs.zanotti.inforinvestigador.data.model.User;
import com.unibs.zanotti.inforinvestigador.data.source.local.dao.IUserLocalDao;

import java.util.List;
import java.util.Optional;

public class DummyUserLocalDao implements IUserLocalDao {
    @Override
    public void save(User user) {

    }

    @Override
    public void update(User user, String[] params) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public Optional<User> get(long id) {
        return Optional.empty();
    }
}
