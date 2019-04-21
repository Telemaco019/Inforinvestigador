package com.unibs.zanotti.inforinvestigador.data.model;

import com.xwray.groupie.ExpandableGroup;

public class ExpandableCommentGroup extends ExpandableGroup {
    public ExpandableCommentGroup(Comment comment, int depth) {
        super(new ExpandableCommentItem(comment, depth));

        for (Comment comm : comment.getChildren()) {
            add(new ExpandableCommentGroup(comm, depth));
            depth++;
        }
    }
}
