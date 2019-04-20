package com.unibs.zanotti.inforinvestigador.data.model;

import java.util.List;

public class PaperShare extends Paper {
    private String sharingUserComment;
    private String sharingUser;
    private int sharingUserProfilePicture;

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
                      int sharingUserProfilePicture) {
        super(paperId, paperTitle, paperAuthors, paperDate, null, null, paperTopics, null);
        this.sharingUserComment = sharingUserComment;
        this.sharingUser = sharingUser;
        this.sharingUserProfilePicture = sharingUserProfilePicture;
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
}
