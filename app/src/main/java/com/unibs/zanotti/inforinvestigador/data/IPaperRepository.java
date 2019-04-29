package com.unibs.zanotti.inforinvestigador.data;


import com.unibs.zanotti.inforinvestigador.domain.model.Comment;
import com.unibs.zanotti.inforinvestigador.domain.model.Paper;
import io.reactivex.Single;

import java.util.List;
import java.util.Optional;

public interface IPaperRepository {
    Optional<Paper> getPaper(String paperId);

    List<Paper> getPapers();

    List<Comment> getComments(long paperId);

    /**
     * Add the {@code comment} provided as argument to the {@link Paper paper} corresponding to the
     * {@code paperId} provided as argument
     *
     * @param paperId
     * @param comment
     * @return
     */
    Single<Comment> addComment(String paperId, Comment comment);

    /**
     * Write that comments are not saved
     * Save in the data layer the {@link Paper paper model object} provided as argument. Please note that the possible
     * comments present into the Paper object are not persisted in the data layer by calling this method (they are
     * supposed to be persisted individually by calling the {@link IPaperRepository#addComment(String, Comment)}
     * proper method})
     *
     * @param paper Paper domain object to persist
     */
    void savePaper(Paper paper);
}
