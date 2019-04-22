package com.unibs.zanotti.inforinvestigador.domain.model


data class FeedPaper (var paperId: Long,
                      var paperTitle: String,
                      var sharingUserComment: String,
                      var sharingUserName: String,
                      var sharingUserProfilePicture: Int,
                      var paperDate: String,
                      var paperTopics: List<String>,
                      var paperAuthors: List<String>)