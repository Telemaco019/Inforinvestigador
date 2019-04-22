package com.unibs.zanotti.inforinvestigador.data.source;

import com.unibs.zanotti.inforinvestigador.data.model.Comment;
import com.unibs.zanotti.inforinvestigador.data.model.Paper;

import java.util.List;
import java.util.Optional;

public interface IPaperService {
    Optional<Paper> getPaper(long paperId);

    List<Paper> getPapers();

    List<Comment> getComments(long paperId);
}
