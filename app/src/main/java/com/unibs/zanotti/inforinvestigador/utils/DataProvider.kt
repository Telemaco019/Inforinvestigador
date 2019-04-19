package com.unibs.zanotti.inforinvestigador.utils

import com.unibs.zanotti.inforinvestigador.R
import com.unibs.zanotti.inforinvestigador.data.PaperSuggestion
import com.unibs.zanotti.inforinvestigador.data.ResearcherSuggestion

class DataProvider {
    companion object {
        fun getPaperSuggestionDataset(): ArrayList<PaperSuggestion> {
            val result = arrayListOf<PaperSuggestion>()
            var paperId = "1"
            var title = "This is the title of the paper"
            var authors = "Devis Bianchini, Marina Zanella, Pietro Baroni"
            var date = "Mar 2019 - "
            var topics = arrayOf("Informatics", "Science", "Math")
            var comment = "This is the comment made by the use user who shared the paper"
            var sharingUser = "Mario Relha"
            var sharingProfilePicture = R.drawable.test_researcher_7

            val suggestion_1 =
                PaperSuggestion(
                    paperId,
                    title,
                    authors,
                    date,
                    topics,
                    comment,
                    sharingUser,
                    sharingProfilePicture
                )
            result.add(suggestion_1)

            paperId = "2"
            title = "This is the title of the second paper"
            authors = "Pippo Baudo, Nicola Adami, Marina Zanella"
            date = "Dec 2018 - "
            topics = arrayOf("Informatics", "Science", "Math")
            comment = "This is the comment made by the use user who shared the paper"
            sharingUser = "Maria Piras"
            sharingProfilePicture = R.drawable.test_researcher_1
            result.add(
                PaperSuggestion(
                    paperId,
                    title,
                    authors,
                    date,
                    topics,
                    comment,
                    sharingUser,
                    sharingProfilePicture
                )
            )

            paperId = "3"
            title = "This is the title of the third paper"
            authors = "Pippo Baudo, Nicola Adami, Marina Zanella"
            date = "May 2018 - "
            topics = arrayOf("History", "Science", "Informatics", "Geography")
            comment = "This is the comment made by the use user who shared the paper"
            sharingUser = "Teresa Sardinha"
            sharingProfilePicture = R.drawable.test_researcher_5
            result.add(
                PaperSuggestion(
                    paperId,
                    title,
                    authors,
                    date,
                    topics,
                    comment,
                    sharingUser,
                    sharingProfilePicture
                )
            )


            paperId = "4"
            title = "This is the title of the fourth paper"
            authors = "Pippo Baudo, Nicola Adami, Marina Zanella"
            date = "May 2018 - "
            topics = arrayOf("History", "Science", "Informatics", "Geography")
            comment = "This is the comment made by the use user who shared the paper"
            sharingUser = "Leonor Freitas"
            sharingProfilePicture = R.drawable.test_researcher_2
            result.add(
                PaperSuggestion(
                    paperId,
                    title,
                    authors,
                    date,
                    topics,
                    comment,
                    sharingUser,
                    sharingProfilePicture
                )
            )

            result.shuffle()
            return result
        }

        fun getResearcherSuggestionDataset(): ArrayList<ResearcherSuggestion> {
            val result = arrayListOf<ResearcherSuggestion>()

            result.add(
                ResearcherSuggestion(
                    R.drawable.test_researcher_1,
                    "Maria Piras"
                )
            )
            result.add(
                ResearcherSuggestion(
                    R.drawable.test_researcher_2,
                    "Leonor Freitas"
                )
            )
            result.add(
                ResearcherSuggestion(
                    R.drawable.test_researcher_3,
                    "Antonio Lopes"
                )
            )
            result.add(
                ResearcherSuggestion(
                    R.drawable.test_researcher_4,
                    "Cristiano Carvalho"
                )
            )
            result.add(
                ResearcherSuggestion(
                    R.drawable.test_researcher_5,
                    "Teresa Sardinha"
                )
            )
            result.add(
                ResearcherSuggestion(
                    R.drawable.test_researcher_6,
                    "Joana de Carvalho"
                )
            )
            result.add(
                ResearcherSuggestion(
                    R.drawable.test_researcher_7,
                    "Mario Relha"
                )
            )

            result.shuffle()
            return result
        }
    }
}