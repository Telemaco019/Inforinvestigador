package com.unibs.zanotti.inforinvestigador.suggestions.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unibs.zanotti.inforinvestigador.R
import com.unibs.zanotti.inforinvestigador.data.ResearcherSuggestion
import de.hdodenhof.circleimageview.CircleImageView

class ResearcherSuggestionAdapter(var dataset: List<ResearcherSuggestion>) :
    RecyclerView.Adapter<ResearcherSuggestionAdapter.MyViewHolder>() {


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView = view.findViewById(R.id.roundImageCaption) as TextView
        val imageView = view.findViewById<CircleImageView>(R.id.roundImage)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Create a new view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.round_image_with_caption, parent, false)

        // Set layout parameters of the view
        // ...

        return MyViewHolder(
            view
        )
    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Get element at this position from the dataset and replace the content of the view with that element
        // val view = holder.textView
        holder.imageView.setImageResource(dataset[position].image)
        holder.textView.text = dataset[position].name
    }

}