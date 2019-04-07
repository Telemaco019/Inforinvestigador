package com.unibs.zanotti.inforinvestigador.navigation

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.widget.TextView
import com.unibs.zanotti.inforinvestigador.LibraryFragment
import com.unibs.zanotti.inforinvestigador.ProfileFragment
import com.unibs.zanotti.inforinvestigador.R
import com.unibs.zanotti.inforinvestigador.recommendation.list.ListRecommendationsFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set the toolbar layout as toolbar
        val toolbar = findViewById(R.id.top_bar) as Toolbar
        setSupportActionBar(toolbar)
        // Set the title of the support action bar
        supportActionBar?.setTitle(R.string.app_name)
        // Set the font of the support action bar
        val textView = toolbar.getChildAt(0) as TextView
        textView.typeface = resources.getFont(R.font.montserrat_light)

        val fragmentManager = supportFragmentManager

        // Navigation fragments
        val fragmentHome = ListRecommendationsFragment()
        val fragmentLibrary = LibraryFragment()
        val fragmentProfile = ProfileFragment()

        val navigationBar = findViewById<BottomNavigationView>(R.id.bottom_navigation_bar)
        navigationBar.setOnNavigationItemSelectedListener { item ->
            val fragment: Fragment
            when (item.itemId) {
                R.id.bottom_bar_action_home -> {
                    fragment = fragmentHome
                }
                R.id.bottom_bar_action_library -> {
                    fragment = fragmentLibrary
                }
                R.id.bottom_bar_action_profile -> {
                    fragment = fragmentProfile
                }
                else -> {
                    fragment = fragmentHome
                }
            }

            fragmentManager.beginTransaction().replace(R.id.fragment_placheholder, fragment).commit()
            return@setOnNavigationItemSelectedListener true
        }

        // Set default selected fragment
        navigationBar.selectedItemId = R.id.bottom_bar_action_home
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        // Put the item of the menu in the toolbar. Material Design is applied automatically
        inflater.inflate(R.menu.menu_top_bar_recommendations, menu)

        return true
    }
}
