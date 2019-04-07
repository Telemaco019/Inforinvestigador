package com.unibs.zanotti.inforinvestigador.common

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.unibs.zanotti.inforinvestigador.R
import com.unibs.zanotti.inforinvestigador.common.model.Paper

class ViewPaperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_paper)

        val text = findViewById<TextView>(R.id.test)
        text.text = intent.getStringExtra(Paper.PAPER_ID_EXTRA)
    }
}
