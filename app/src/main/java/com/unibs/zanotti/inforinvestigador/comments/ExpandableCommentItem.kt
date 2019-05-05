package com.unibs.zanotti.inforinvestigador.comments

import android.view.LayoutInflater
import android.view.View
import com.unibs.zanotti.inforinvestigador.R
import com.unibs.zanotti.inforinvestigador.domain.model.Comment
import com.unibs.zanotti.inforinvestigador.domain.utils.DateUtils
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.expandable_comment.view.*
import java.time.LocalDateTime

class ExpandableCommentItem(
    val comment: Comment,
    val depth: Int,
    val replyListener: OnReplyClickedListener
) : Item<ViewHolder>(), ExpandableItem {

    private lateinit var expandableGroup: ExpandableGroup

    interface OnReplyClickedListener {
        fun onReplyClicked(item: Item<ViewHolder>, comment: Comment)
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        addingDepthViews(viewHolder)

        viewHolder.itemView.tv_user.text = comment.author
        viewHolder.itemView.body.text = comment.body
        viewHolder.itemView.tv_votes.text = comment.score.toString()
        viewHolder.itemView.tv_comment_date.text = DateUtils.elapsedTime(comment.dateTime, LocalDateTime.now(), "now")

        viewHolder.itemView.apply {
            setOnLongClickListener {
                expandableGroup.onToggleExpanded()
                true
            }
        }

        viewHolder.itemView.reply_to_comment_clickable_tv.setOnClickListener {
            replyListener.onReplyClicked(
                this,
                comment
            )
        }
    }

    private fun addingDepthViews(viewHolder: ViewHolder) {
        viewHolder.itemView.separatorContainer.removeAllViews()
        viewHolder.itemView.separatorContainer.visibility = if (depth > 0) View.VISIBLE else View.GONE

        for (i in 1..depth) {
            val v: View = LayoutInflater.from(viewHolder.itemView.context)
                .inflate(R.layout.layout_separator_view, viewHolder.itemView.separatorContainer, false)
            viewHolder.itemView.separatorContainer.addView(v)
        }
        viewHolder.itemView.body.requestLayout()
    }

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        this.expandableGroup = onToggleListener

        // Expand comments (show comments expanded by default)
        onToggleListener.onToggleExpanded()
    }

    override fun getLayout(): Int {
        return R.layout.expandable_comment
    }
}
