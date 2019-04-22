package com.unibs.zanotti.inforinvestigador.data.source.local;

import com.unibs.zanotti.inforinvestigador.data.model.Comment;
import com.unibs.zanotti.inforinvestigador.data.model.Paper;
import com.unibs.zanotti.inforinvestigador.data.source.IPaperService;
import com.unibs.zanotti.inforinvestigador.data.source.local.dao.IPaperLocalDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PaperLocalService implements IPaperService {
    private static volatile PaperLocalService INSTANCE = null;

    private final IPaperLocalDao paperDao;

    private PaperLocalService(IPaperLocalDao paperDao) {
        this.paperDao = paperDao;
    }

    public static PaperLocalService getInstance(IPaperLocalDao paperDao) {
        if(INSTANCE==null) {
            INSTANCE = new PaperLocalService(paperDao);
        }
        return INSTANCE;
    }

    @Override
    public Optional<Paper> getPaper(long paperId) {
        return paperDao.get(paperId);
    }

    @Override
    public List<Paper> getPapers() {
        return paperDao.getAll();
    }

    @Override
    public List<Comment> getComments(long paperId) {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(
                "Body of the comment",
                "Author",
                123,
                1l,
                new ArrayList<>()
        ));
        comments.add(new Comment(
                "Body of the comment",
                "Author 2",
                13,
                2l,
                Arrays.asList(new Comment("Sub comment","Author 1",3,3l,new ArrayList<>()))
        ));

        return comments;
    }
}
