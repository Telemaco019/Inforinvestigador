package com.unibs.zanotti.inforinvestigador.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDateTime;

public class Comment implements Parcelable {
    private String body;
    private String author;
    private int likesCount;
    private String id;
    private LocalDateTime dateTime;
    private String paperId;
    /**
     * Flag that indicates if the user currently logged into Inforinvestigador has liked the comment
     */
    private boolean likedByCurrentUser;

    public Comment(String body,
                   String author,
                   int likesCount,
                   String id,
                   LocalDateTime dateTime,
                   String paperId) {
        this.body = body;
        this.author = author;
        this.likesCount = likesCount;
        this.id = id;
        this.dateTime = dateTime;
        this.paperId = paperId;

        this.likedByCurrentUser = false;
    }

    protected Comment(Parcel in) {
        body = in.readString();
        author = in.readString();
        likesCount = in.readInt();
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
        dest.writeInt(likesCount);
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

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
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

    public boolean isLikedByCurrentUser() {
        return likedByCurrentUser;
    }

    public void setLikedByCurrentUser(boolean likedByCurrentUser) {
        this.likedByCurrentUser = likedByCurrentUser;
    }
}
