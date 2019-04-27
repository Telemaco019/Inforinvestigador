package com.unibs.zanotti.inforinvestigador.data.remote.model;

import java.util.List;

public class CommentEntity {
    private String body;
    private String author;
    private int score;
    private Long id;
    private List<Long> childrenCommentIDs;
}
