
package com.unibs.zanotti.inforinvestigador.domain.model.crossref;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Author {

    @SerializedName("ORCID")
    @Expose
    private String oRCID;
    @SerializedName("authenticated-orcid")
    @Expose
    private Boolean authenticatedOrcid;
    @SerializedName("given")
    @Expose
    private String given;
    @SerializedName("family")
    @Expose
    private String family;
    @SerializedName("sequence")
    @Expose
    private String sequence;
    @SerializedName("affiliation")
    @Expose
    private List<Object> affiliation = null;

    public String getORCID() {
        return oRCID;
    }

    public void setORCID(String oRCID) {
        this.oRCID = oRCID;
    }

    public Boolean getAuthenticatedOrcid() {
        return authenticatedOrcid;
    }

    public void setAuthenticatedOrcid(Boolean authenticatedOrcid) {
        this.authenticatedOrcid = authenticatedOrcid;
    }

    public String getGiven() {
        return given;
    }

    public void setGiven(String given) {
        this.given = given;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public List<Object> getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(List<Object> affiliation) {
        this.affiliation = affiliation;
    }

}
