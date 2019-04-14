package com.unibs.zanotti.inforinvestigador.common.model

data class Comment constructor(val body : String,
                               val author : String,
                               val score : Int,
                               val id : String,
                               val children : List<Comment>)