package com.unibs.zanotti.inforinvestigador.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDateTime;

public class Comment implements Parcelable {
    private String body;
    private String author;
    private int score;
    private String id;
    private LocalDateTime dateTime;
    private String paperId;

    public Comment(String body,
                   String author,
                   int score,
                   String id,
                   LocalDateTime dateTime,
                   String paperId) {
        this.body = body;
        this.author = author;
        this.score = score;
        this.id = id;
        this.dateTime = dateTime;
        this.paperId = paperId;
    }

    protected Comment(Parcel in) {
        body = in.readString();
        author = in.readString();
        score = in.readInt();
        id = in.readString();
        dateTime = LocalDateTime.parse(in.readString());
        paperId = in.readString();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(body);
        dest.writeString(author);
        dest.writeInt(score);
        dest.writeString(id);
        dest.writeString(dateTime.toString());
        dest.writeString(paperId);
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
