package com.unibs.zanotti.inforinvestigador.data.source.local.dao.impl;

import com.unibs.zanotti.inforinvestigador.data.model.Paper;
import com.unibs.zanotti.inforinvestigador.data.source.local.dao.IPaperLocalDao;

import java.util.List;
import java.util.Optional;

public class DummyPaperLocalDao implements IPaperLocalDao {
    @Override
    public void save(Paper paper) {
        // Do nothing
    }

    @Override
    public void update(Paper paper, String[] params) {
        // Do nothing
    }

    @Override
    public void delete(Paper paper) {
        // Do nothing
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
