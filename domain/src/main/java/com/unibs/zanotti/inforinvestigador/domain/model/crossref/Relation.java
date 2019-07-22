
package com.unibs.zanotti.inforinvestigador.domain.model.crossref;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Relation {

    @SerializedName("cites")
    @Expose
    private List<Object> cites = null;

    public List<Object> getCites() {
        return cites;
    }

    public void setCites(List<Object> cites) {
        this.cites = cites;
    }

}
