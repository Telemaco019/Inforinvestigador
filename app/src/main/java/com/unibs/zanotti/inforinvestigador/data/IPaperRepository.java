package com.unibs.zanotti.inforinvestigador.data;


import com.unibs.zanotti.inforinvestigador.domain.model.Comment;
import com.unibs.zanotti.inforinvestigador.domain.model.Paper;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Interface for saving/retrieving/updating paper-related model entities to/from the data layer.
 * <p> Please note that the model entities are supposed to have ID generated within the data layer. For this reason,
 * it is assumed that a model entity has ID null until it is persisted to the data layer, where an id is assigned to
 * it according to the specific implementation of the repository. </p>
 */
public interface IPaperRepository {
    Maybe<Paper> getPaper(String paperId);

    Observable<Paper> getPapers();

    /**
     * Add the {@code comment} provided as argument to the {@link Paper paper} corresponding to the
     * {@code comment} provided as argument. The Comment is persisted into the db using a new autogenerated id.
     *
     * @param comment
     * @return A Single on which an Observer can subscribe in order to get the comment that has been added (or an error
     * if something goes wrong during the adding operation)
     */
    Single<Comment> saveComment(Comment comment);

    /**
     * Save  in the data layer the {@link Paper paper model object} provided as argument, using an autogenerated id
     *
     * @param paper Paper domain object to persist
     * @return A Single on which an Observer can subscribe in order to get the paper that has been added (or an error
     * if something goes wrong during the adding operation)
     */
    Single<Paper> savePaper(Paper paper);

    Completable likeComment(String paperId, String commentId, String userId);

    Completable unlikeComment(String paperId, String commentId, String userId);

    /**
     *
     * @param paperId
     * @param currentUserId User id of the user currently logged into Inforinvestigador, in order to determine which
     *                      comments have been liked by him and marked them as "liked"
     * @return
     */
    Observable<Comment> getCommentsRealTime(String paperId, String currentUserId);
}
