package com.unibs.zanotti.inforinvestigador.data.source.remote.dao.impl;

import com.unibs.zanotti.inforinvestigador.data.model.Paper;
import com.unibs.zanotti.inforinvestigador.data.source.remote.dao.IRemotePaperDao;

import java.util.List;
import java.util.Optional;

public class RemotePaperDao implements IRemotePaperDao {
    @Override
    public void save(Paper paper) {

    }

    @Override
    public void update(Paper paper, String[] params) {

    }

    @Override
    public void delete(Paper paper) {

    }

    @Override
    public List<Paper> getAll() {
        return null;
    }

    @Override
    public Optional<Paper> get(long id) {
        return Optional.empty();
    }
}
