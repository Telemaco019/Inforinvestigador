package com.unibs.zanotti.inforinvestigador.data.local;

import com.unibs.zanotti.inforinvestigador.data.IPaperRepository;
import com.unibs.zanotti.inforinvestigador.data.local.dao.IPaperLocalDao;
import com.unibs.zanotti.inforinvestigador.domain.model.Comment;
import com.unibs.zanotti.inforinvestigador.domain.model.Paper;
import io.reactivex.Single;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PaperLocalRepository implements IPaperRepository {
    private static volatile PaperLocalRepository INSTANCE = null;

    private final IPaperLocalDao paperDao;

    private PaperLocalRepository(IPaperLocalDao paperDao) {
        this.paperDao = paperDao;
    }

    public static PaperLocalRepository getInstance(IPaperLocalDao paperDao) {
        if (INSTANCE == null) {
            INSTANCE = new PaperLocalRepository(paperDao);
        }
        return INSTANCE;
    }

    @Override
    public Optional<Paper> getPaper(String paperId) {
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
                "1",
                new ArrayList<Comment>()
        ));
        comments.add(new Comment(
                "Body of the comment",
                "Author 2",
                13,
                "2",
                Arrays.asList(new Comment("Sub comment", "Author 1", 3, "3", new ArrayList<Comment>()))
        ));
        comments.add(new Comment(
                "Body of the comment",
                "Author 2",
                13,
                "2",
                Arrays.asList(new Comment("Sub comment", "Author 1", 3, "3", new ArrayList<Comment>()))
        ));

        return comments;
    }

    @Override
    public Single<Comment> addComment(String paperId, Comment comment) {
        return null;
    }

    @Override
    public void savePaper(Paper paper) {

    }
}
