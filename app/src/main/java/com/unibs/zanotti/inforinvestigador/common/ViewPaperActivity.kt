package com.unibs.zanotti.inforinvestigador.common

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.unibs.zanotti.inforinvestigador.R


class ViewPaperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_paper)
        setupSupportActionBar()
    }

    private fun setupSupportActionBar() {
        val toolbar = findViewById<Toolbar>(R.id.top_bar)
        setSupportActionBar(toolbar)

        // Show back icon
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Hide app title
        supportActionBar?.setDisplayShowTitleEnabled(false)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        // If the home item has been clicked (e.g. the back arrow), then close this activity
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_top_bar_view_paper, menu)
        return true
    }
}
