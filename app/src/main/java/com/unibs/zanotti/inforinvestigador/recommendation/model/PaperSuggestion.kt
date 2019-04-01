package com.unibs.zanotti.inforinvestigador.recommendation.model

class PaperSuggestion(
    val paperTitle: String,
    val paperAuthors: String,
    val paperDate: String,
    val paperTopics: Array<String>,
    val paperComment: String,
    val sharingUser: String,
    val sharingUserProfilePicture: Int
)
