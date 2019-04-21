package com.unibs.zanotti.inforinvestigador.data.source.local.dao.impl;

import com.unibs.zanotti.inforinvestigador.data.model.Paper;
import com.unibs.zanotti.inforinvestigador.data.source.local.dao.IPaperLocalDao;

import java.util.Arrays;
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
        Paper paper = new Paper(1l,
                "Title",
                Arrays.asList("Author 1", "Author 2"),
                "Mar 2019",
                "11023ff0123",
                23,
                Arrays.asList("Topic 1", "Topic 2"),
                "This is the long long long abstract",
                "IEEE");
        return Optional.of(paper);
    }
}
