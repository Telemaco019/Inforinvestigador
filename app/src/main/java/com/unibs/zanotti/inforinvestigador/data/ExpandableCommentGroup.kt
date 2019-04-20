package com.unibs.zanotti.inforinvestigador.data

import com.xwray.groupie.ExpandableGroup

class ExpandableCommentGroup constructor(comment: Comment, depth: Int = 0) :
    ExpandableGroup(ExpandableCommentItem(comment, depth)) {

    init {
        for (comm in comment.children) {
            add(ExpandableCommentGroup(comm, depth.plus(1)))
        }
    }
}