package com.example.moviesapp.domain.repository

import com.example.moviesapp.common.Resource
import com.example.moviesapp.domain.model.LocalAgencyMovie
import com.example.moviesapp.domain.model.NetflixMovie
import com.example.moviesapp.domain.model.TopMoviesTVs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getTopMovies(
        fetchRemote : Boolean
    ) : Flow<Resource<TopMoviesTVs>>

    suspend fun getTopTvs(
        fetchRemote : Boolean
    ) : Flow<Resource<TopMoviesTVs>>

    suspend fun getLocalAgencyMovies() : Flow<Resource<List<LocalAgencyMovie>>>

    @ExperimentalCoroutinesApi
    suspend fun getNetflixData(): Flow<Resource<List<NetflixMovie>>>


}