package com.example.moviesapp.data.repository

import com.example.moviesapp.common.Resource
import com.example.moviesapp.data.csv.CSVParser
import com.example.moviesapp.data.local.MovieDatabase
import com.example.moviesapp.data.remote.MoviesRemoteDataSource
import com.example.moviesapp.domain.model.LocalAgencyMovie
import com.example.moviesapp.domain.model.NetflixMovie
import com.example.moviesapp.domain.model.TopMoviesTVs
import com.example.moviesapp.domain.repository.MoviesRepository
import io.ktor.utils.io.jvm.javaio.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException


class MoviesRepositoryImpl (
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    private val localAgencyMoviesParser: CSVParser<LocalAgencyMovie>,
    db:MovieDatabase
):MoviesRepository {

    private val dao = db.dao

    override suspend fun getTopMovies(fetchRemote: Boolean): Flow<Resource<TopMoviesTVs>> {
        return flow {
            emit(Resource.Loading(true))
            val movies = try {
                moviesRemoteDataSource.getTopMovies()
            }catch(e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            movies?.let {
                emit(Resource.Success(data = it))
            }
        }
    }

    override suspend fun getTopTvs(fetchRemote: Boolean): Flow<Resource<TopMoviesTVs>> {
        return flow {
            emit(Resource.Loading(true))
            val movies = try {
                moviesRemoteDataSource.getTopTvs()
            }catch(e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            movies?.let {
                emit(Resource.Success(data = it))
            }
        }
    }

    override suspend fun getLocalAgencyMovies(): Flow<Resource<List<LocalAgencyMovie>>> {
        return flow {
            emit(Resource.Loading(true))
            val remoteMoves = try {
                val response = moviesRemoteDataSource.getLocalAgencyMovies()
                localAgencyMoviesParser.parse(response.content.toInputStream())
            }catch (e:IOException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }
            remoteMoves?.let {
                emit(Resource.Success(data = it))
            }
        }
    }

    @ExperimentalCoroutinesApi
    override suspend fun getNetflixData() : Flow<Resource<List<NetflixMovie>>> {
        return moviesRemoteDataSource.getNetflixMovies()
    }




}
