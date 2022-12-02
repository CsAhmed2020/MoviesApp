package com.example.moviesapp.domain.model

data class NetflixMovie(
    val title:String,
    val type:String,
    val description:String,
    val releaseYear:String,
    val ageCertification:String,
    val runtime:String,
    val genres:String,
    val productionCountries:String,
    val seasons:String,
    val imdbScore:String,
    val imdbVotes:String,
    val tmdbPopularity:String,
    val tmdbScore:String
)
