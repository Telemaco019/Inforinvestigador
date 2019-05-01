package com.unibs.zanotti.inforinvestigador.data.remote.model;

import java.util.List;

public class PaperEntity {

    private String id;
    private String paperTitle;
    private List<String> paperAuthors;
    private String paperDate;
    private String paperDoi;
    private Integer paperCitations;
    private List<String> paperTopics;
    private String paperAbstract;
    private String paperPublisher;
    private String sharingUserId;
    private String sharingUserComment;

    public PaperEntity() {
        // Required by Firestone
    }

    public PaperEntity(String id,
                       String paperTitle,
                       List<String> paperAuthors,
                       String paperDate, String paperDoi,
                       int paperCitations,
                       List<String> paperTopics,
                       String paperAbstract,
                       String paperPublisher,
                       String sharingUserId,
                       String sharingUserComment) {
        this.id = id;
        this.paperTitle = paperTitle;
        this.paperAuthors = paperAuthors;
        this.paperDate = paperDate;
        this.paperDoi = paperDoi;
        this.paperCitations = paperCitations;
        this.paperTopics = paperTopics;
        this.paperAbstract = paperAbstract;
        this.paperPublisher = paperPublisher;
        this.sharingUserId = sharingUserId;
        this.sharingUserComment = sharingUserComment;
    }

    public String getSharingUserId() {
        return sharingUserId;
    }

    public void setSharingUserId(String sharingUserId) {
        this.sharingUserId = sharingUserId;
    }

    public String getSharingUserComment() {
        return sharingUserComment;
    }

    public void setSharingUserComment(String sharingUserComment) {
        this.sharingUserComment = sharingUserComment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public List<String> getPaperAuthors() {
        return paperAuthors;
    }

    public void setPaperAuthors(List<String> paperAuthors) {
        this.paperAuthors = paperAuthors;
    }

    public String getPaperDate() {
        return paperDate;
    }

    public void setPaperDate(String paperDate) {
        this.paperDate = paperDate;
    }

    public String getPaperPublisher() {
        return paperPublisher;
    }

    public void setPaperPublisher(String paperPublisher) {
        this.paperPublisher = paperPublisher;
    }

    public String getPaperDoi() {
        return paperDoi;
    }

    public void setPaperDoi(String paperDoi) {
        this.paperDoi = paperDoi;
    }


    public String getPaperAbstract() {
        return paperAbstract;
    }

    public void setPaperAbstract(String paperAbstract) {
        this.paperAbstract = paperAbstract;
    }

    public Integer getPaperCitations() {
        return paperCitations;
    }

    public void setPaperCitations(Integer paperCitations) {
        this.paperCitations = paperCitations;
    }

    public List<String> getPaperTopics() {
        return paperTopics;
    }

    public void setPaperTopics(List<String> paperTopics) {
        this.paperTopics = paperTopics;
    }
}
