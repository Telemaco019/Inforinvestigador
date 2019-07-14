package com.unibs.zanotti.inforinvestigador.data.remote.model;

import java.util.HashMap;
import java.util.Map;

public class CommentEntity {
    private String body;
    private String authorName;
    private int likesCount;
    private String id;
    private long epochTimestampMillis;
    private Map<String,Boolean> likes;
    private String authorId;

    public CommentEntity() {
        // Required by Firestore
    }


    public CommentEntity(String body,
                         String authorName,
                         int likesCount,
                         String id,
                         long epochTimestampMillis,
                         String authorId) {
        this.body = body;
        this.authorName = authorName;
        this.likesCount = likesCount;
        this.id = id;
        this.epochTimestampMillis = epochTimestampMillis;
        this.authorId = authorId;
        this.likes = new HashMap<>();
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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
