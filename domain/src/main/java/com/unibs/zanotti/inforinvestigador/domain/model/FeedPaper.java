package com.unibs.zanotti.inforinvestigador.domain.model;

import android.net.Uri;

import java.util.List;

public class FeedPaper {
    String paperId;
    private String paperTitle;
    private String sharingUserComment;
    private String sharingUserName;
    private Uri sharingUserProfilePicture;
    private String paperDate;
    private List<String> paperTopics;
    private List<String> paperAuthors;

    public FeedPaper(String paperId,
                     String paperTitle,
                     String sharingUserComment,
                     String sharingUserName,
                     Uri sharingUserProfilePicture,
                     String paperDate,
                     List<String> paperTopics,
                     List<String> paperAuthors) {
        this.paperId = paperId;
        this.paperTitle = paperTitle;
        this.sharingUserComment = sharingUserComment;
        this.sharingUserName = sharingUserName;
        this.sharingUserProfilePicture = sharingUserProfilePicture;
        this.paperDate = paperDate;
        this.paperTopics = paperTopics;
        this.paperAuthors = paperAuthors;
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
}
