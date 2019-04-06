package com.unibs.zanotti.inforinvestigador.recommendation.list


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.unibs.zanotti.inforinvestigador.R
import com.unibs.zanotti.inforinvestigador.common.Actions
import com.unibs.zanotti.inforinvestigador.recommendation.model.PaperSuggestion
import com.unibs.zanotti.inforinvestigador.recommendation.model.ResearcherSuggestion

class ListRecommendationsFragment : Fragment(), PaperSuggestionAdapter.OnPaperSuggestionListener {
    override fun onPaperSuggestionClick() {
        val intent = Intent(Actions.SHOW_PAPER)
        intent.putExtra(PaperSuggestion.PAPER_ID_NUMBER_EXTRA,5)
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_recommendations, container, false)

        view.findViewById<RecyclerView>(R.id.recommended_researchers_recycler).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = ResearcherSuggestionAdapter(getResearcherSuggestionDataset())
        }

        val paperSuggestionAdapter = PaperSuggestionAdapter(getPaperSuggestionDataset(), this)
        view.findViewById<RecyclerView>(R.id.testRecycleView).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = paperSuggestionAdapter
        }

        return view
    }

    private fun getPaperSuggestionDataset(): ArrayList<PaperSuggestion> {
        val result = arrayListOf<PaperSuggestion>()
        var title = "This is the title of the paper"
        var authors = "Devis Bianchini, Marina Zanella, Pietro Baroni"
        var date = "Mar 2019 - "
        var topics = arrayOf("Informatics", "Science", "Math")
        var comment = "This is the comment made by the use user who shared the paper"
        var sharingUser = "Mario Relha"
        var sharingProfilePicture = R.drawable.test_researcher_7

        val suggestion_1 = PaperSuggestion(title, authors, date, topics, comment, sharingUser, sharingProfilePicture)
        result.add(suggestion_1)

        title = "This is the title of the second paper"
        authors = "Pippo Baudo, Nicola Adami, Marina Zanella"
        date = "Dec 2018 - "
        topics = arrayOf("Informatics", "Science", "Math")
        comment = "This is the comment made by the use user who shared the paper"
        sharingUser = "Maria Piras"
        sharingProfilePicture = R.drawable.test_researcher_1
        result.add(PaperSuggestion(title, authors, date, topics, comment, sharingUser, sharingProfilePicture))

        title = "This is the title of the third paper"
        authors = "Pippo Baudo, Nicola Adami, Marina Zanella"
        date = "May 2018 - "
        topics = arrayOf("History", "Science", "Informatics", "Geography")
        comment = "This is the comment made by the use user who shared the paper"
        sharingUser = "Teresa Sardinha"
        sharingProfilePicture = R.drawable.test_researcher_5
        result.add(PaperSuggestion(title, authors, date, topics, comment, sharingUser, sharingProfilePicture))


        title = "This is the title of the fourth paper"
        authors = "Pippo Baudo, Nicola Adami, Marina Zanella"
        date = "May 2018 - "
        topics = arrayOf("History", "Science", "Informatics", "Geography")
        comment = "This is the comment made by the use user who shared the paper"
        sharingUser = "Leonor Freitas"
        sharingProfilePicture = R.drawable.test_researcher_2
        result.add(PaperSuggestion(title, authors, date, topics, comment, sharingUser, sharingProfilePicture))

        result.shuffle()
        return result
    }

    private fun getResearcherSuggestionDataset(): ArrayList<ResearcherSuggestion> {
        val result = arrayListOf<ResearcherSuggestion>()

        result.add(ResearcherSuggestion(R.drawable.test_researcher_1, "Maria Piras"))
        result.add(ResearcherSuggestion(R.drawable.test_researcher_2, "Leonor Freitas"))
        result.add(ResearcherSuggestion(R.drawable.test_researcher_3, "Antonio Lopes"))
        result.add(ResearcherSuggestion(R.drawable.test_researcher_4, "Cristiano Carvalho"))
        result.add(ResearcherSuggestion(R.drawable.test_researcher_5, "Teresa Sardinha"))
        result.add(ResearcherSuggestion(R.drawable.test_researcher_6, "Joana de Carvalho"))
        result.add(ResearcherSuggestion(R.drawable.test_researcher_7, "Mario Relha"))

        result.shuffle()
        return result
    }
}
