package com.unibs.zanotti.inforinvestigador.data.source.local;

import com.unibs.zanotti.inforinvestigador.data.model.Comment;
import com.unibs.zanotti.inforinvestigador.data.model.PaperShare;
import com.unibs.zanotti.inforinvestigador.data.source.IPaperShareDatasource;
import com.unibs.zanotti.inforinvestigador.data.source.local.dao.IPaperShareLocalDao;
import com.unibs.zanotti.inforinvestigador.data.source.local.dao.impl.DummyPaperShareLocalDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaperShareLocalDatasource implements IPaperShareDatasource {

    private static volatile PaperShareLocalDatasource INSTANCE;

    private IPaperShareLocalDao paperSharesDao;

    private PaperShareLocalDatasource() {
        // To be replaced with injection
        paperSharesDao = new DummyPaperShareLocalDao();
    }

    public static PaperShareLocalDatasource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PaperShareLocalDatasource();
        }
        return INSTANCE;
    }

    @Override
    public List<PaperShare> getPaperShares() {
        return paperSharesDao.getAll();
    }

    @Override
    public List<Comment> getComments(long paperShareId) {
        Optional<PaperShare> paperShare = paperSharesDao.get(paperShareId);
        return paperShare.isPresent() ?
                paperShare.get().getComments() :
                new ArrayList<>();
    }
}
