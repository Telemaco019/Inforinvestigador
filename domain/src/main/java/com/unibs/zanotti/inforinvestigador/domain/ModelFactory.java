package com.unibs.zanotti.inforinvestigador.domain;

import com.unibs.zanotti.inforinvestigador.domain.model.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ModelFactory {
    /**
     * Creates a new comment with the body and author name specified as argument. The created comment has
     * a score of 0 and has no children.
     *
     * @param body
     * @param authorName
     * @return
     */
    public static Comment createComment(String body, String authorName) {
        return new Comment(body, authorName, 0, null, LocalDateTime.now(), new ArrayList<>());
    }
}
