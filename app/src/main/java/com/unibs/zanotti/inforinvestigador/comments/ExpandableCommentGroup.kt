package com.unibs.zanotti.inforinvestigador.comments

import com.unibs.zanotti.inforinvestigador.data.Comment
import com.xwray.groupie.ExpandableGroup

class ExpandableCommentGroup constructor(comment: Comment, depth: Int = 0) :
    ExpandableGroup(ExpandableCommentItem(comment, depth)) {

    init {
        for (comm in comment.children) {
            add(ExpandableCommentGroup(comm, depth.plus(1)))
        }
    }
}