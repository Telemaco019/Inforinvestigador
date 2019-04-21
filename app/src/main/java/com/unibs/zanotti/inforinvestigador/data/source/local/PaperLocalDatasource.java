package com.unibs.zanotti.inforinvestigador.data.source.local;

import com.unibs.zanotti.inforinvestigador.data.model.Paper;
import com.unibs.zanotti.inforinvestigador.data.source.IPaperDatasource;
import com.unibs.zanotti.inforinvestigador.data.source.local.dao.IPaperLocalDao;

import java.util.Optional;

public class PaperLocalDatasource implements IPaperDatasource {
    private final IPaperLocalDao paperDao;

    public PaperLocalDatasource(IPaperLocalDao paperDao) {
        this.paperDao = paperDao;
    }

    @Override
    public Optional<Paper> getPaper(long paperId) {
        return paperDao.get(paperId);
    }
}
