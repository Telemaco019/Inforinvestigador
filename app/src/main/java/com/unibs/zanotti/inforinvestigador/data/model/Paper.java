package com.unibs.zanotti.inforinvestigador.data.model;

import java.util.List;

public class Paper {
    private Long paperId;
    private String paperTitle;
    private List<String> paperAuthors;
    private String paperDate;
    private String paperDoi;
    private int paperCitations;
    private List<String> paperTopics;
    private String paperAbstract;
    private String paperPublisher;

    public Paper(Long paperId,
                 String paperTitle,
                 List<String> paperAuthors,
                 String paperDate, String paperDoi,
                 int paperCitations,
                 List<String> paperTopics,
                 String paperAbstract,
                 String paperPublisher) {
        this.paperId = paperId;
        this.paperTitle = paperTitle;
        this.paperAuthors = paperAuthors;
        this.paperDate = paperDate;
        this.paperDoi = paperDoi;
        this.paperCitations = paperCitations;
        this.paperTopics = paperTopics;
        this.paperAbstract = paperAbstract;
        this.paperPublisher = paperPublisher;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
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

    public int getPaperCitations() {
        return paperCitations;
    }

    public String getPaperAbstract() {
        return paperAbstract;
    }

    public void setPaperAbstract(String paperAbstract) {
        this.paperAbstract = paperAbstract;
    }

    public void setPaperCitations(int paperCitations) {
        this.paperCitations = paperCitations;
    }

    public List<String> getPaperTopics() {
        return paperTopics;
    }

    public void setPaperTopics(List<String> paperTopics) {
        this.paperTopics = paperTopics;
    }
}
