package com.unibs.zanotti.inforinvestigador.data.remote.model;

public class CommentEntity {
    private String body;
    private String author;
    private int score;
    private String id;
    private long epochTimestampMillis;

    public CommentEntity() {
        // Required by Firestore
    }

    public CommentEntity(String body,
                         String author,
                         int score,
                         String id,
                         long epochTimestampMillis) {
        this.body = body;
        this.author = author;
        this.score = score;
        this.id = id;
        this.epochTimestampMillis = epochTimestampMillis;
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
}
