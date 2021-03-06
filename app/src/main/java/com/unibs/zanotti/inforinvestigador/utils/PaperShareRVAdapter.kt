package com.unibs.zanotti.inforinvestigador.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.unibs.zanotti.inforinvestigador.R
import com.unibs.zanotti.inforinvestigador.domain.model.PaperShare
import com.unibs.zanotti.inforinvestigador.utils.itemTouch.ItemTouchHelperAdapter
import de.hdodenhof.circleimageview.CircleImageView
import java.util.Collections.swap

class PaperShareRVAdapter(var dataset: MutableList<PaperShare>, val listener: OnPaperShareListener, val currentUserId: String) :
    RecyclerView.Adapter<PaperShareRVAdapter.MyViewHolder>(),
    ItemTouchHelperAdapter {

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                swap(dataset, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                swap(dataset, i, i - 1)
            }
        }

        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        listener.onPaperShareDismissed(dataset[position].paperId)
        dataset.removeAt(position)
        notifyItemRemoved(position)
    }



    inner class MyViewHolder(view: View) :
        RecyclerView.ViewHolder(view),
        View.OnClickListener {

        var tvPaperTitle = view.findViewById<TextView>(R.id.shared_paper_title)
        var tvPaperAuthors = view.findViewById<TextView>(R.id.shared_paper_authors)
        var tvPaperDate = view.findViewById<TextView>(R.id.shared_paper_date)
        var tvPaperTopics = view.findViewById<TextView>(R.id.shared_paper_topics)
        var tvPaperComment = view.findViewById<TextView>(R.id.shared_paper_comment)
        var tvSharingUser = view.findViewById<TextView>(R.id.shared_paper_sharing_user_name)
        var tvSharingUserProfilePicture = view.findViewById<CircleImageView>(R.id.shared_paper_sharing_user_picture)
        var imEditPaperShare = view.findViewById<ImageButton>(R.id.edit_paper_share_button)

        init {
            view.setOnClickListener(this)

            tvSharingUser.setOnClickListener {
                listener.onPaperSharingUserClick(dataset[adapterPosition].sharingUserId)
            }

            tvSharingUserProfilePicture.setOnClickListener {
                listener.onPaperSharingUserClick(dataset[adapterPosition].sharingUserId)
            }

            imEditPaperShare.setOnClickListener {
                listener.onEditPaperShareClicked(dataset[adapterPosition].paperId)
            }
        }

        override fun onClick(v: View?) {
            listener.onPaperShareClicked(dataset[adapterPosition].paperId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Create a new view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_shared_paper, parent, false)

        // Set layout parameters of the view
        // ...

        return MyViewHolder(view)
    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Get element at this position from the dataset and replace the content of the view with that element
        val paperTitle = dataset[position].paperTitle
        val authors = dataset[position].paperAuthors?.joinToString(separator = ", ")
        val paperDate = dataset[position].paperDate
        val topics = dataset[position].paperTopics?.joinToString(separator = ", ")
        val sharingUserComment = dataset[position].sharingUserComment
        val sharingUserName = dataset[position].sharingUserName

        holder.tvPaperTitle.text = paperTitle ?: ""
        holder.tvPaperAuthors.text = authors ?: ""
        holder.tvPaperDate.text = paperDate ?: ""
        holder.tvPaperTopics.text = topics ?: ""
        holder.tvPaperComment.text = sharingUserComment ?: ""
        holder.tvSharingUser.text = sharingUserName ?: ""
        Picasso.get()
            .load(dataset[position].sharingUserProfilePicture)
            .placeholder(R.drawable.user_profle_pic_placeholder)
            .fit()
            .centerCrop()
            .into(holder.tvSharingUserProfilePicture)
        if (currentUserId == dataset[position].getSharingUserId()) {
            holder.imEditPaperShare.visibility = View.VISIBLE
        }
    }

    interface OnPaperShareListener {
        fun onPaperShareClicked(paperId: String)

        fun onPaperSharingUserClick(userId: String)

        fun onPaperShareDismissed(paperId: String)

        fun onEditPaperShareClicked(paperId: String)
    }
}