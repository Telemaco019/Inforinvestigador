package com.unibs.zanotti.inforinvestigador.data.model;

import java.util.List;

public class PaperShare extends Paper {
    private String sharingUserComment;
    private String sharingUser;
    private int sharingUserProfilePicture;
    private ExpandableCommentGroup comments;

    public PaperShare() {
        super();
    }

    public PaperShare(Long paperId,
                      String paperTitle,
                      List<String> paperAuthors,
                      String paperDate,
                      List<String> paperTopics,
                      String sharingUserComment,
                      String sharingUser,
                      int sharingUserProfilePicture,
                      ExpandableCommentGroup comments) {
        super(paperId, paperTitle, paperAuthors, paperDate, null, null, paperTopics);
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

    public ExpandableCommentGroup getComments() {
        return comments;
    }

    public void setComments(ExpandableCommentGroup comments) {
        this.comments = comments;
    }
}
