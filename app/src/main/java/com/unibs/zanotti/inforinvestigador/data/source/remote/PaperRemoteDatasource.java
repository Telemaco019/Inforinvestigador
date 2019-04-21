package com.unibs.zanotti.inforinvestigador.data.source.remote;

import com.unibs.zanotti.inforinvestigador.data.model.Paper;
import com.unibs.zanotti.inforinvestigador.data.source.IPaperDatasource;
import com.unibs.zanotti.inforinvestigador.data.source.remote.dao.IPaperRemoteDao;

import java.util.Optional;

public class PaperRemoteDatasource implements IPaperDatasource {
    private IPaperRemoteDao paperDao;

    public PaperRemoteDatasource(IPaperRemoteDao paperDao) {
        this.paperDao = paperDao;
    }

    @Override
    public Optional<Paper> getPaper(long paperId) {
        return paperDao.get(paperId);
    }
}
