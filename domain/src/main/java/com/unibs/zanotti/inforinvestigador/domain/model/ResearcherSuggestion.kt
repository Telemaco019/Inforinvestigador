package com.unibs.zanotti.inforinvestigador.domain.model

import android.net.Uri

data class ResearcherSuggestion(
    val researcherId: String,
    val imageUri: Uri,
    val name: String
)