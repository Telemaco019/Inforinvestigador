package com.unibs.zanotti.inforinvestigador.data.model;

import java.util.List;

public class PaperShare extends Paper {
    private String sharingUserComment;
    private String sharingUser;
    private int sharingUserProfilePicture;
    private List<ExpandableCommentGroup> comments;
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
            List<ExpandableCommentGroup> comments) {
        super(paperId, paperTitle, paperAuthors, paperDate, null, null, paperTopics);
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

    public List<ExpandableCommentGroup> getComments() {
        return comments;
    }

    public void setComments(List<ExpandableCommentGroup> comments) {
        this.comments = comments;
    }

    public long getPaperShareId() {
        return paperShareId;
    }

    public void setPaperShareId(long paperShareId) {
        this.paperShareId = paperShareId;
    }
}
