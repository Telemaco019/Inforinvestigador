
package com.unibs.zanotti.inforinvestigador.domain.model.crossref;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reference {

    @SerializedName("issue")
    @Expose
    private String issue;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("doi-asserted-by")
    @Expose
    private String doiAssertedBy;
    @SerializedName("first-page")
    @Expose
    private String firstPage;
    @SerializedName("DOI")
    @Expose
    private String dOI;
    @SerializedName("volume")
    @Expose
    private String volume;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("unstructured")
    @Expose
    private String unstructured;
    @SerializedName("journal-title")
    @Expose
    private String journalTitle;
    @SerializedName("volume-title")
    @Expose
    private String volumeTitle;

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDoiAssertedBy() {
        return doiAssertedBy;
    }

    public void setDoiAssertedBy(String doiAssertedBy) {
        this.doiAssertedBy = doiAssertedBy;
    }

    public String getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(String firstPage) {
        this.firstPage = firstPage;
    }

    public String getDOI() {
        return dOI;
    }

    public void setDOI(String dOI) {
        this.dOI = dOI;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getUnstructured() {
        return unstructured;
    }

    public void setUnstructured(String unstructured) {
        this.unstructured = unstructured;
    }

    public String getJournalTitle() {
        return journalTitle;
    }

    public void setJournalTitle(String journalTitle) {
        this.journalTitle = journalTitle;
    }

    public String getVolumeTitle() {
        return volumeTitle;
    }

    public void setVolumeTitle(String volumeTitle) {
        this.volumeTitle = volumeTitle;
    }

}
