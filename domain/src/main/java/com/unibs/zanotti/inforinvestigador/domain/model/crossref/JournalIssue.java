
package com.unibs.zanotti.inforinvestigador.domain.model.crossref;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JournalIssue {

    @SerializedName("published-print")
    @Expose
    private PublishedPrint_ publishedPrint;
    @SerializedName("issue")
    @Expose
    private String issue;

    public PublishedPrint_ getPublishedPrint() {
        return publishedPrint;
    }

    public void setPublishedPrint(PublishedPrint_ publishedPrint) {
        this.publishedPrint = publishedPrint;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

}
