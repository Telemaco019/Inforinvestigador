package com.unibs.zanotti.inforinvestigador.domain.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class FeedPaper implements Parcelable {
    private String paperId;
    private String paperTitle;
    private String sharingUserComment;
    private String sharingUserName;
    private Uri sharingUserProfilePicture;
    private String paperDate;
    private List<String> paperTopics;
    private List<String> paperAuthors;
    private String sharingUserId;

    public FeedPaper(String paperId,
                     String paperTitle,
                     String sharingUserComment,
                     String sharingUserName,
                     Uri sharingUserProfilePicture,
                     String paperDate,
                     List<String> paperTopics,
                     List<String> paperAuthors,
                     String sharingUserId) {
        this.paperId = paperId;
        this.paperTitle = paperTitle;
        this.sharingUserComment = sharingUserComment;
        this.sharingUserName = sharingUserName;
        this.sharingUserProfilePicture = sharingUserProfilePicture;
        this.paperDate = paperDate;
        this.paperTopics = paperTopics;
        this.paperAuthors = paperAuthors;
        this.sharingUserId = sharingUserId;
    }

    private FeedPaper(Parcel in) {
        this.paperId = in.readString();
        this.paperTitle = in.readString();
        this.sharingUserComment = in.readString();
        this.sharingUserName = in.readString();
        this.sharingUserProfilePicture = Uri.parse(in.readString());
        this.paperDate = in.readString();
        in.readList(paperTopics, String.class.getClassLoader());
        in.readList(paperAuthors, String.class.getClassLoader());
        this.sharingUserId = in.readString();
    }

    public static final Creator<FeedPaper> CREATOR = new Creator<FeedPaper>() {
        @Override
        public FeedPaper createFromParcel(Parcel source) {
            return new FeedPaper(source);
        }

        @Override
        public FeedPaper[] newArray(int size) {
            return new FeedPaper[0];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(paperId);
        dest.writeString(paperTitle);
        dest.writeString(sharingUserComment);
        dest.writeString(sharingUserName);
        dest.writeString(sharingUserProfilePicture.toString());
        dest.writeString(paperDate);
        dest.writeList(paperTopics);
        dest.writeList(paperAuthors);
        dest.writeString(sharingUserId);
    }

    public String getSharingUserId() {
        return sharingUserId;
    }

    public void setSharingUserId(String sharingUserId) {
        this.sharingUserId = sharingUserId;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public String getSharingUserComment() {
        return sharingUserComment;
    }

    public void setSharingUserComment(String sharingUserComment) {
        this.sharingUserComment = sharingUserComment;
    }

    public String getSharingUserName() {
        return sharingUserName;
    }

    public void setSharingUserName(String sharingUserName) {
        this.sharingUserName = sharingUserName;
    }

    public Uri getSharingUserProfilePicture() {
        return sharingUserProfilePicture;
    }

    public void setSharingUserProfilePicture(Uri sharingUserProfilePicture) {
        this.sharingUserProfilePicture = sharingUserProfilePicture;
    }

    public String getPaperDate() {
        return paperDate;
    }

    public void setPaperDate(String paperDate) {
        this.paperDate = paperDate;
    }

    public List<String> getPaperTopics() {
        return paperTopics;
    }

    public void setPaperTopics(List<String> paperTopics) {
        this.paperTopics = paperTopics;
    }

    public List<String> getPaperAuthors() {
        return paperAuthors;
    }

    public void setPaperAuthors(List<String> paperAuthors) {
        this.paperAuthors = paperAuthors;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
