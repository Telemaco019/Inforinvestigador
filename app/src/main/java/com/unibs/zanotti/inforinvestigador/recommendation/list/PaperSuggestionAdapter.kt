package com.unibs.zanotti.inforinvestigador.recommendation.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.unibs.zanotti.inforinvestigador.R

// TODO
class PaperSuggestionAdapter(private val dataset: ArrayList<String>) :
    RecyclerView.Adapter<PaperSuggestionAdapter.MyViewHolder>() {


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
       // var text = view.findViewById<TextView>(R.id.testText)
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
        // val view = holder.textView
      //  holder.text.setText(dataset[position])
    }

}