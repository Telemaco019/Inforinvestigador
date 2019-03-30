package com.unibs.zanotti.inforinvestigador.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.unibs.zanotti.inforinvestigador.R
import com.unibs.zanotti.inforinvestigador.ResearcherSuggestionAdapter
import com.unibs.zanotti.inforinvestigador.TestAdapter
import com.unibs.zanotti.inforinvestigador.model.ResearcherSuggestion

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        view.findViewById<RecyclerView>(R.id.recommended_researchers_recycler).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            adapter = ResearcherSuggestionAdapter(getDataset())
        }

        view.findViewById<RecyclerView>(R.id.testRecycleView).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = TestAdapter(getTestDataset())
        }

        return view
    }

    private fun getTestDataset(): ArrayList<String> {
        return arrayListOf("Item 1","Item 2","Item 2","Item 2","Item 2","Item 2","Item 2","Item 2","Item 2")
    }

    private fun getDataset(): ArrayList<ResearcherSuggestion> {
        val result = arrayListOf<ResearcherSuggestion>()

        result.add(ResearcherSuggestion(R.drawable.test_researcher_1,"Maria Piras"))
        result.add(ResearcherSuggestion(R.drawable.test_researcher_2,"Leonor Freitas"))
        result.add(ResearcherSuggestion(R.drawable.test_researcher_3,"Antonio Lopes"))
        result.add(ResearcherSuggestion(R.drawable.test_researcher_4,"Cristiano Carvalho"))
        result.add(ResearcherSuggestion(R.drawable.test_researcher_5,"Teresa Sardinha"))
        result.add(ResearcherSuggestion(R.drawable.test_researcher_6,"Joana de Carvalho"))
        result.add(ResearcherSuggestion(R.drawable.test_researcher_7,"Mario Relha"))

        result.shuffle()
        return result
    }
}
