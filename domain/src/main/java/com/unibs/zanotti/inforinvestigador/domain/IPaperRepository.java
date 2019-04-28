package com.unibs.zanotti.inforinvestigador.domain;


import com.unibs.zanotti.inforinvestigador.domain.model.Comment;
import com.unibs.zanotti.inforinvestigador.domain.model.Paper;

import java.util.List;
import java.util.Optional;

public interface IPaperRepository {
    Optional<Paper> getPaper(long paperId);

    List<Paper> getPapers();

    List<Comment> getComments(long paperId);
}
