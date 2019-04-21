package com.unibs.zanotti.inforinvestigador.data.source;

import com.unibs.zanotti.inforinvestigador.data.model.Paper;

import java.util.Optional;

public interface IPaperDatasource {
    Optional<Paper> getPaper(long paperId);
}
