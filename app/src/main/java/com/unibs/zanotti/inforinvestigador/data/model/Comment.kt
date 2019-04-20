package com.unibs.zanotti.inforinvestigador.data.model

data class Comment constructor(val body : String,
                               val author : String,
                               val score : Int,
                               val id : String,
                               val children : List<Comment>)