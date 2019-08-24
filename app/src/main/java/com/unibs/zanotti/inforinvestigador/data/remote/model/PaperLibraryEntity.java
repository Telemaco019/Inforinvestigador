package com.unibs.zanotti.inforinvestigador.data.remote.model;

import java.util.List;


public class PaperLibraryEntity {
    private List<String> paperIds;

    public PaperLibraryEntity() {
    }

    public PaperLibraryEntity(List<String> paperIds) {
        this.paperIds = paperIds;
    }

    public List<String> getPaperIds() {
        return paperIds;
    }

    public void setPaperIds(List<String> paperIds) {
        this.paperIds = paperIds;
    }
}
