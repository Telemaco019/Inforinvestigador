package com.unibs.zanotti.inforinvestigador.data.source.local.dao.impl;

import com.unibs.zanotti.inforinvestigador.data.model.Paper;
import com.unibs.zanotti.inforinvestigador.data.source.local.dao.ILocalPaperDao;

import java.util.List;
import java.util.Optional;

public class DummyLocalPaperDao implements ILocalPaperDao {
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
