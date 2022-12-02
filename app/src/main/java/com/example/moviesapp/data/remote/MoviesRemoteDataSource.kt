package com.example.moviesapp.data.remote

import com.example.moviesapp.common.Resource
import com.example.moviesapp.domain.model.NetflixMovie
import com.example.moviesapp.domain.model.TopMoviesTVs
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow

interface MoviesRemoteDataSource {

    suspend fun getTopMovies() : TopMoviesTVs

    suspend fun getTopTvs() : TopMoviesTVs

    suspend fun getLocalAgencyMovies() : HttpResponse

    suspend fun getNetflixMovies() : Flow<Resource<List<NetflixMovie>>>


}