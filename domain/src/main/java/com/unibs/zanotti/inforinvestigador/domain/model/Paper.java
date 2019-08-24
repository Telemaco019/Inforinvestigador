package com.unibs.zanotti.inforinvestigador.domain.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.unibs.zanotti.inforinvestigador.domain.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Paper implements Parcelable {
    private String paperId;
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
    private List<Uri> paperImages;
    private String URL;

    public Paper() {
        paperAuthors = new ArrayList<>();
        paperTopics = new ArrayList<>();
        paperImages = new ArrayList<>();
    }

    public Paper(String paperId,
                 String paperTitle,
                 List<String> paperAuthors,
                 String paperDate,
                 String paperDoi,
                 Integer paperCitations,
                 List<String> paperTopics,
                 String paperAbstract,
                 String paperPublisher,
                 String sharingUserId,
                 String sharingUserComment,
                 List<Uri> paperImages,
                 String link) {
        this.paperId = paperId;
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
        this.URL = link;
    }

    protected Paper(Parcel in) {
        paperId = in.readString();
        paperTitle = in.readString();
        paperAuthors = in.createStringArrayList();
        paperDate = in.readString();
        paperDoi = in.readString();
        if (in.readByte() == 0) {
            paperCitations = null;
        } else {
            paperCitations = in.readInt();
        }
        paperTopics = in.createStringArrayList();
        paperAbstract = in.readString();
        paperPublisher = in.readString();
        sharingUserId = in.readString();
        sharingUserComment = in.readString();
        paperImages = in.createTypedArrayList(Uri.CREATOR);
        URL = in.readString();
    }

    public static final Creator<Paper> CREATOR = new Creator<Paper>() {
        @Override
        public Paper createFromParcel(Parcel in) {
            return new Paper(in);
        }

        @Override
        public Paper[] newArray(int size) {
            return new Paper[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(paperId);
        dest.writeString(paperTitle);
        dest.writeList(paperAuthors);
        dest.writeString(paperDate);
        dest.writeString(paperDoi);
        dest.writeInt(paperCitations);
        dest.writeList(paperTopics);
        dest.writeString(paperAbstract);
        dest.writeString(paperPublisher);
        dest.writeString(sharingUserId);
        dest.writeString(sharingUserId);
        dest.writeString(sharingUserComment);
        dest.writeList(paperImages);
        dest.writeString(URL);
    }

    public String getURL() {
        return StringUtils.isBlank(URL) ? StringUtils.BLANK : URL;
    }

    public void setURL(String url) {
        this.URL = url;
    }

    public void setPaperImages(List<Uri> paperImages) {
        this.paperImages = paperImages;
    }

    public String getSharingUserId() {
        return StringUtils.isBlank(sharingUserId) ? StringUtils.BLANK : sharingUserId;
    }

    public void setSharingUserId(String sharingUserId) {
        this.sharingUserId = sharingUserId;
    }

    public String getSharingUserComment() {
        return StringUtils.isBlank(sharingUserComment) ? StringUtils.BLANK : sharingUserComment;
    }

    public void setSharingUserComment(String sharingUserComment) {
        this.sharingUserComment = sharingUserComment;
    }

    public String getPaperId() {
        return StringUtils.isBlank(paperId) ? StringUtils.BLANK : paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getPaperTitle() {
        return StringUtils.isBlank(paperTitle) ? StringUtils.BLANK : paperTitle;
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

    public void addPaperAuthor(String author) {
        this.paperAuthors.add(author);
    }

    public String getPaperDate() {
        return StringUtils.isBlank(paperDate) ? StringUtils.BLANK : paperDate;
    }

    public void setPaperDate(String paperDate) {
        this.paperDate = paperDate;
    }

    public String getPaperPublisher() {
        return StringUtils.isBlank(paperPublisher) ? StringUtils.BLANK : paperPublisher;
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

    public Integer getPaperCitations() {
        return paperCitations;
    }

    public String getPaperAbstract() {
        return StringUtils.isBlank(paperAbstract) ? StringUtils.BLANK : paperAbstract;
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

    public List<Uri> getPaperImages() {
        return paperImages;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
