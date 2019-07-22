
package com.unibs.zanotti.inforinvestigador.domain.model.crossref;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PublishedPrint_ {

    @SerializedName("date-parts")
    @Expose
    private List<List<Long>> dateParts = null;

    public List<List<Long>> getDateParts() {
        return dateParts;
    }

    public void setDateParts(List<List<Long>> dateParts) {
        this.dateParts = dateParts;
    }

}
