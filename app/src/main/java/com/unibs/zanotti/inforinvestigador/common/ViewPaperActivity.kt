package com.unibs.zanotti.inforinvestigador.common

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
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
        // Set the title of the support action bar
        supportActionBar?.setTitle(R.string.app_name)
        // Set the font of the support action bar
        val textView = toolbar.getChildAt(0) as TextView
        textView.typeface = resources.getFont(R.font.montserrat_light)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        when (id) {
            // If the home item has been clicked (e.g. the back arrow), then close this activity
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.menu_topbar_innermenu_addToLibrary -> {
                Toast.makeText(this,R.string.msg_success_paper_added_to_library,Toast.LENGTH_SHORT).show()
            }
            R.id.menu_topbar_innermenu_addToPaperlist -> {
                // TODO
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_top_bar_view_paper, menu)
        return true
    }
}
