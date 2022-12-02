package com.example.moviesapp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopMoviesTVs(
    @SerialName("items")
    var topItems : List<TopItem>
)
