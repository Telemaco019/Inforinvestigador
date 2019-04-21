package com.unibs.zanotti.inforinvestigador.homefeed.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unibs.zanotti.inforinvestigador.R
import com.unibs.zanotti.inforinvestigador.data.model.PaperShare
import de.hdodenhof.circleimageview.CircleImageView

class PaperShareAdapter(var dataset: List<PaperShare>, val listener: OnPaperShareListener) :
    RecyclerView.Adapter<PaperShareAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View, val listener: OnPaperShareListener) :
        RecyclerView.ViewHolder(view),
        View.OnClickListener {

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onPaperShareClick(dataset[adapterPosition].paperId)
        }

        var tvPaperTitle = view.findViewById<TextView>(R.id.paper_title)
        var tvPaperAuthors = view.findViewById<TextView>(R.id.paper_authors)
        var tvPaperDate = view.findViewById<TextView>(R.id.paper_date)
        var tvPaperTopics = view.findViewById<TextView>(R.id.paper_topics)
        var tvPaperComment = view.findViewById<TextView>(R.id.tv_shared_paper)
        var tvSharingUser = view.findViewById<TextView>(R.id.tv_shared_by)
        var tvSharingUserProfilePicture = view.findViewById<CircleImageView>(R.id.sharing_user_picture)
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
        holder.tvPaperTitle.text = dataset[position].paperTitle
        holder.tvPaperAuthors.text = dataset[position].paperAuthors.joinToString(separator = ", ")
        holder.tvPaperDate.text = dataset[position].paperDate
        holder.tvPaperTopics.text = dataset[position].paperTopics.joinToString { t -> t }
        holder.tvPaperComment.text = dataset[position].sharingUserComment
        holder.tvSharingUser.text = dataset[position].sharingUser
        holder.tvSharingUserProfilePicture.setImageResource(dataset[position].sharingUserProfilePicture)
    }

    interface OnPaperShareListener {
        fun onPaperShareClick(paperId: Long)
    }
}