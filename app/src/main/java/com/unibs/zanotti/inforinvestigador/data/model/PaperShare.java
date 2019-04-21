package com.unibs.zanotti.inforinvestigador.data.model;

import java.util.List;

public class PaperShare {
    private String paperTitle;
    private String paperDate;
    private List<String> paperTopics;
    private List<String> paperAuthors;
    private Long paperId;
    private String sharingUserComment;
    private String sharingUser;
    private int sharingUserProfilePicture;
    private List<Comment> comments;
    private long paperShareId;

    public PaperShare() {
        super();
    }

    public PaperShare(
            Long paperShareId,
            Long paperId,
            String paperTitle,
            List<String> paperAuthors,
            String paperDate,
            List<String> paperTopics,
            String sharingUserComment,
            String sharingUser,
            int sharingUserProfilePicture,
            List<Comment> comments) {
        this.paperId = paperId;
        this.paperTitle = paperTitle;
        this.paperAuthors = paperAuthors;
        this.paperDate = paperDate;
        this.paperTopics = paperTopics;
        this.paperShareId = paperShareId;
        this.sharingUserComment = sharingUserComment;
        this.sharingUser = sharingUser;
        this.sharingUserProfilePicture = sharingUserProfilePicture;
        this.comments = comments;
    }

    public String getSharingUserComment() {
        return sharingUserComment;
    }

    public void setSharingUserComment(String sharingUserComment) {
        this.sharingUserComment = sharingUserComment;
    }

    public String getSharingUser() {
        return sharingUser;
    }

    public void setSharingUser(String sharingUser) {
        this.sharingUser = sharingUser;
    }

    public int getSharingUserProfilePicture() {
        return sharingUserProfilePicture;
    }

    public void setSharingUserProfilePicture(int sharingUserProfilePicture) {
        this.sharingUserProfilePicture = sharingUserProfilePicture;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public long getPaperShareId() {
        return paperShareId;
    }

    public void setPaperShareId(long paperShareId) {
        this.paperShareId = paperShareId;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
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

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }
}
