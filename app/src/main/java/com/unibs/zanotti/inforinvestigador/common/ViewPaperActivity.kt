package com.unibs.zanotti.inforinvestigador.common

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.unibs.zanotti.inforinvestigador.R
import com.unibs.zanotti.inforinvestigador.comments.ExpandableCommentGroup
import com.unibs.zanotti.inforinvestigador.data.Comment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_view_paper.*


class ViewPaperActivity : AppCompatActivity() {
    private val groupAdapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_paper)
        setupSupportActionBar()

        view_paper_comments_rv.apply {
            layoutManager = GridLayoutManager(applicationContext, groupAdapter.spanCount).apply {
                spanSizeLookup = groupAdapter.spanSizeLookup
            }
            adapter = groupAdapter
        }

        groupAdapter.add(getTestCommentsData())
    }

    private fun getTestCommentsData(): ExpandableCommentGroup {
        val list = ArrayList<Comment>()
        val list2 = ArrayList<Comment>()


        //list2.add(Comment("This is the comment body","Author",1,"5", emptyList()))
        //list2.add(Comment("This is the comment body","Author",1,"6", emptyList()))
        list2.add(
            Comment(
                "This is the comment body",
                "Author",
                1,
                "7",
                emptyList()
            )
        )

        list.add(
            Comment(
                "This is the comment body",
                "Author",
                1,
                "5",
                emptyList()
            )
        )
        list.add(
            Comment(
                "This is the comment body",
                "Author",
                14,
                "2",
                emptyList()
            )
        )
        list.add(
            Comment(
                "This is the comment body",
                "Author",
                11,
                "3",
                emptyList()
            )
        )
        list.add(Comment("This is the comment body", "Author", 23, "4", list2))


        return ExpandableCommentGroup(
            Comment("This is the comment body", "Author", 1, "1", list)
        )
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
                Toast.makeText(this, R.string.msg_success_paper_added_to_library, Toast.LENGTH_SHORT).show()
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
