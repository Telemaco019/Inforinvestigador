package com.unibs.zanotti.inforinvestigador.recommendation.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.unibs.zanotti.inforinvestigador.R
import com.unibs.zanotti.inforinvestigador.recommendation.model.PaperSuggestion
import de.hdodenhof.circleimageview.CircleImageView

class PaperSuggestionAdapter(private val dataset: ArrayList<PaperSuggestion>, val listener: OnPaperSuggestionListener) :
    RecyclerView.Adapter<PaperSuggestionAdapter.MyViewHolder>() {

    class MyViewHolder(view: View, val listener: OnPaperSuggestionListener) :
        RecyclerView.ViewHolder(view),
        View.OnClickListener {

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onPaperSuggestionClick()
        }

        var tvPaperTitle = view.findViewById<TextView>(R.id.paper_title)
        var tvPaperAuthors = view.findViewById<TextView>(R.id.paper_authors)
        var tvPaperDate = view.findViewById<TextView>(R.id.paper_date)
        var tvPaperTopics = view.findViewById<TextView>(R.id.paper_topics)
        var tvPaperComment = view.findViewById<TextView>(R.id.shared_paper_caption)
        var tvSharingUser = view.findViewById<TextView>(R.id.shared_by_caption)
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
        holder.tvPaperAuthors.text = dataset[position].paperAuthors
        holder.tvPaperDate.text = dataset[position].paperDate
        holder.tvPaperTopics.text = dataset[position].paperTopics.joinToString { t -> t }
        holder.tvPaperComment.text = dataset[position].paperComment
        holder.tvSharingUser.text = dataset[position].sharingUser
        holder.tvSharingUserProfilePicture.setImageResource(dataset[position].sharingUserProfilePicture)
    }

    interface OnPaperSuggestionListener {
        fun onPaperSuggestionClick()
    }
}