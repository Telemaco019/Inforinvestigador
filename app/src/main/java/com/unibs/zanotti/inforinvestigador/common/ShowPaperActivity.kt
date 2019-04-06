package com.unibs.zanotti.inforinvestigador.common

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.unibs.zanotti.inforinvestigador.R
import com.unibs.zanotti.inforinvestigador.recommendation.model.PaperSuggestion

class ShowPaperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_paper)

        val paperSuggestionNumber = intent.getIntExtra(PaperSuggestion.PAPER_ID_NUMBER_EXTRA, -1)
        if (paperSuggestionNumber != -1) {
            findViewById<TextView>(R.id.paper_suggestion_id).text = paperSuggestionNumber.toString()
        }
    }
}
