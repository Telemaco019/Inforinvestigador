package com.unibs.zanotti.inforinvestigador.recommendation.list


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unibs.zanotti.inforinvestigador.R
import com.unibs.zanotti.inforinvestigador.common.Actions
import com.unibs.zanotti.inforinvestigador.common.model.Paper
import com.unibs.zanotti.inforinvestigador.recommendation.model.PaperSuggestion
import com.unibs.zanotti.inforinvestigador.recommendation.model.ResearcherSuggestion
import com.unibs.zanotti.inforinvestigador.util.DataProvider

class ListRecommendationsFragment : androidx.fragment.app.Fragment(), PaperSuggestionAdapter.OnPaperSuggestionListener {
    var paperSuggestions: ArrayList<PaperSuggestion>
    var researcherSuggestions: ArrayList<ResearcherSuggestion>

    init {
        paperSuggestions = DataProvider.getPaperSuggestionDataset()
        researcherSuggestions = DataProvider.getResearcherSuggestionDataset()
    }

    override fun onPaperSuggestionClick(suggestionNumber: Int) {
        val paperId = paperSuggestions[suggestionNumber].paperId
        val intent = Intent(Actions.SHOW_PAPER)
        intent.putExtra(Paper.PAPER_ID_EXTRA,paperId)
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        view.findViewById<RecyclerView>(R.id.recommended_researchers_recycler).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = ResearcherSuggestionAdapter(researcherSuggestions)

        }

        val paperSuggestionAdapter = PaperSuggestionAdapter(paperSuggestions, this)
        view.findViewById<RecyclerView>(R.id.testRecycleView).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = paperSuggestionAdapter
        }

        return view
    }


}
