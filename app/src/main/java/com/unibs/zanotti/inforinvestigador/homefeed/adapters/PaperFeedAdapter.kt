package com.unibs.zanotti.inforinvestigador.homefeed.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.unibs.zanotti.inforinvestigador.R
import com.unibs.zanotti.inforinvestigador.domain.model.FeedPaper
import de.hdodenhof.circleimageview.CircleImageView

class PaperFeedAdapter(var dataset: List<FeedPaper>, val listener: OnPaperShareListener) :
    RecyclerView.Adapter<PaperFeedAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View, val listener: OnPaperShareListener) :
        RecyclerView.ViewHolder(view),
        View.OnClickListener {

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onPaperShareClick(dataset[adapterPosition].paperId)
        }

        var tvPaperTitle = view.findViewById<TextView>(R.id.shared_paper_title)
        var tvPaperAuthors = view.findViewById<TextView>(R.id.shared_paper_authors)
        var tvPaperDate = view.findViewById<TextView>(R.id.shared_paper_date)
        var tvPaperTopics = view.findViewById<TextView>(R.id.shared_paper_topics)
        var tvPaperComment = view.findViewById<TextView>(R.id.shared_paper_comment)
        var tvSharingUser = view.findViewById<TextView>(R.id.shared_paper_sharing_user_name)
        var tvSharingUserProfilePicture = view.findViewById<CircleImageView>(R.id.shared_paper_sharing_user_picture)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Create a new view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_shared_paper, parent, false)

        // Set layout parameters of the view
        // ...

        return MyViewHolder(view, listener)
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
    }

    interface OnPaperShareListener {
        fun onPaperShareClick(paperId: String)
    }
}