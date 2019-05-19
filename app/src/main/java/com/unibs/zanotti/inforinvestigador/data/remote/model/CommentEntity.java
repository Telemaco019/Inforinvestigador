package com.unibs.zanotti.inforinvestigador.data.remote.model;

import java.util.HashMap;
import java.util.Map;

public class CommentEntity {
    private String body;
    private String author;
    private int likesCount;
    private String id;
    private long epochTimestampMillis;
    private Map<String,Boolean> likes;

    public CommentEntity() {
        // Required by Firestore
    }


    public CommentEntity(String body,
                         String author,
                         int likesCount,
                         String id,
                         long epochTimestampMillis) {
        this.body = body;
        this.author = author;
        this.likesCount = likesCount;
        this.id = id;
        this.epochTimestampMillis = epochTimestampMillis;
        this.likes = new HashMap<>();
    }

    public long getEpochTimestampMillis() {
        return epochTimestampMillis;
    }

    public void setEpochTimestampMillis(long epochTimestampMillis) {
        this.epochTimestampMillis = epochTimestampMillis;
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

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public Map<String, Boolean> getLikes() {
        return likes;
    }

    public void setLikes(Map<String, Boolean> likes) {
        this.likes = likes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
