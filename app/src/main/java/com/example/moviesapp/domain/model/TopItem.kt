package com.example.moviesapp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopItem(
    @SerialName("id")
    var id: String?= null,
    @SerialName("rank")
    var rank: String?= null,
    @SerialName("rankUpDown")
    var rankUpDown: String?= null,
    @SerialName("title")
    var movieName: String?= null,
    @SerialName("fullTitle")
    var fullTitle: String?= null,
    @SerialName("year")
    var year: String?= null,
    @SerialName("image")
    var image: String?= null,
    @SerialName("crew")
    var crew: String?= null,
    @SerialName("imDbRating")
    var imDbRating: String?= null,
    @SerialName("imDbRatingCount")
    var imDbRatingCount: String?= null,
)