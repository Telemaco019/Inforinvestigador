package com.unibs.zanotti.inforinvestigador.data.source;

import com.unibs.zanotti.inforinvestigador.data.model.ExpandableCommentGroup;
import com.unibs.zanotti.inforinvestigador.data.model.PaperShare;

import java.util.List;

/**
 * Main entry point for accessing shared papers data
 */
public interface IPaperShareDatasource {
    List<PaperShare> getPaperShares();

    List<ExpandableCommentGroup> getPaperShareComments(long paperShareId);
}
