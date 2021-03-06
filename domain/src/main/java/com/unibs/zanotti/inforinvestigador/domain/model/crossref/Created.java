
package com.unibs.zanotti.inforinvestigador.domain.model.crossref;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Created {

    @SerializedName("date-parts")
    @Expose
    private List<List<Long>> dateParts = null;
    @SerializedName("date-time")
    @Expose
    private String dateTime;
    @SerializedName("timestamp")
    @Expose
    private Long timestamp;

    public List<List<Long>> getDateParts() {
        return dateParts;
    }

    public void setDateParts(List<List<Long>> dateParts) {
        this.dateParts = dateParts;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

}
