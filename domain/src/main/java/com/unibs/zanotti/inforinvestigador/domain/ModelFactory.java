package com.unibs.zanotti.inforinvestigador.domain;

import com.unibs.zanotti.inforinvestigador.domain.model.Comment;

import java.time.LocalDateTime;

public class ModelFactory {
    /**
     * Creates a new comment with the body and author name specified as argument. The created comment has
     * a score of 0.
     * <p> Please note that, since it is assumed that a model entity has ID null until it is persisted to the data
     * layer (where and id is automatically generated by the specific persistence provider), the brand-new comment
     * created by this method has id null as well. </p>
     *
     * @param body
     * @param paperId
     * @param authorName
     * @return
     */
    public static Comment createComment(String paperId, String body, String authorName,String authorId) {
        return new Comment(body,
                authorName,
                0,
                null,
                LocalDateTime.now(),
                paperId,
                authorId);
    }
}
