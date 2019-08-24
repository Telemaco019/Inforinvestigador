package com.unibs.zanotti.inforinvestigador.feature.homefeed.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.unibs.zanotti.inforinvestigador.R
import com.unibs.zanotti.inforinvestigador.domain.model.ResearcherSuggestion
import de.hdodenhof.circleimageview.CircleImageView

class ResearcherSuggestionAdapter(
    var dataset: List<ResearcherSuggestion>,
    val listener: OnResearcherSuggestionListener
) : RecyclerView.Adapter<ResearcherSuggestionAdapter.MyViewHolder>() {


    inner class MyViewHolder(view: View, val listener: OnResearcherSuggestionListener) :
        RecyclerView.ViewHolder(view),
        View.OnClickListener {
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onResearcherSuggestionClick(dataset[adapterPosition].researcherId)
        }

        val textView = view.findViewById(R.id.roundImageCaption) as TextView
        val imageView = view.findViewById<CircleImageView>(R.id.roundImage)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Create a new view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.round_image_with_caption, parent, false)

        return MyViewHolder(view, listener)
    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Picasso.get()
            .load(dataset[position].imageUri)
            .placeholder(R.drawable.user_profle_pic_placeholder)
            .into(holder.imageView)
        holder.textView.text = dataset[position].name
    }

    interface OnResearcherSuggestionListener {
        fun onResearcherSuggestionClick(researcherId: String)
    }
}