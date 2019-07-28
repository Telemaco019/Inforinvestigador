package com.unibs.zanotti.inforinvestigador.data.remote.model;

import com.google.common.collect.Lists;

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
    private List<String> paperImages;
    private String url;

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
                       String sharingUserComment,
                       List<String> paperImages,
                       String url) {
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
        this.paperImages = paperImages;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getPaperImages() {
        return paperImages == null ? Lists.newArrayList() : paperImages;
    }

    public void setPaperImages(List<String> paperImages) {
        this.paperImages = paperImages;
    }

    public String getSharingUserId() {
        return sharingUserId;
    }

    public void setSharingUserId(String sharingUserId) {
        this.sharingUserId = sharingUserId;
    }

    public String getSharingUserComment() {
        return sharingUserComment == null? null: sharingUserComment.trim();
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
        return paperAuthors == null ? Lists.newArrayList() : paperAuthors;
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
        return paperCitations == null ? 0 : paperCitations;
    }

    public void setPaperCitations(Integer paperCitations) {
        this.paperCitations = paperCitations;
    }

    public List<String> getPaperTopics() {
        return paperTopics == null ? Lists.newArrayList() : paperTopics;
    }

    public void setPaperTopics(List<String> paperTopics) {
        this.paperTopics = paperTopics;
    }
}
