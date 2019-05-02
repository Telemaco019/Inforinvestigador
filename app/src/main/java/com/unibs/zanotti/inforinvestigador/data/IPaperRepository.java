package com.unibs.zanotti.inforinvestigador.data;


import com.unibs.zanotti.inforinvestigador.domain.model.Comment;
import com.unibs.zanotti.inforinvestigador.domain.model.Paper;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface IPaperRepository {
    Maybe<Paper> getPaper(String paperId);

    Observable<Paper> getPapers();

    /**
     * Add the {@code comment} provided as argument to the {@link Paper paper} corresponding to the
     * {@code paperId} provided as argument
     *
     * @param paperId
     * @param comment
     * @return A Single on which an Observer can subscribe in order to get the comment that has been added (or an error
     * if something goes wrong during the adding operation)
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
     * @return A Single on which an Observer can subscribe in order to get the paper that has been added (or an error
     * if something goes wrong during the adding operation)
     */
    Single<Paper> savePaper(Paper paper);

    Observable<Comment> getComments(String paperId);
}
