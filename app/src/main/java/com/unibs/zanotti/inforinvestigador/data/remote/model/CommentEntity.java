package com.unibs.zanotti.inforinvestigador.data.remote.model;

import java.util.List;

public class CommentEntity {
    private String body;
    private String author;
    private int score;
    private String id;
    private List<String> childrenCommentIDs;

    public CommentEntity(String body, String author, int score, String id, List<String> childrenCommentIDs) {
        this.body = body;
        this.author = author;
        this.score = score;
        this.id = id;
        this.childrenCommentIDs = childrenCommentIDs;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getChildrenCommentIDs() {
        return childrenCommentIDs;
    }

    public void setChildrenCommentIDs(List<String> childrenCommentIDs) {
        this.childrenCommentIDs = childrenCommentIDs;
    }
}
