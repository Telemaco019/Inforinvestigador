package com.unibs.zanotti.inforinvestigador.comments

import com.unibs.zanotti.inforinvestigador.common.model.Comment
import com.xwray.groupie.ExpandableGroup

class ExpandableCommentGroup(comment: Comment, depth: Int = 0) :
    ExpandableGroup(ExpandableCommentItem(comment, depth)) {
    init {
        for (comm in comment.children) {
            add(ExpandableCommentGroup(comment,depth.plus(1)))
        }
    }
}