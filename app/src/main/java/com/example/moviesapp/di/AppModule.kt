package com.example.moviesapp.di

import android.app.Application
import androidx.room.Room
import com.example.moviesapp.common.Constants
import com.example.moviesapp.data.csv.CSVParser
import com.example.moviesapp.data.csv.LocalAgencyMoviesParser
import com.example.moviesapp.data.csv.NetflixMoviesParser
import com.example.moviesapp.data.local.MovieDatabase
import com.example.moviesapp.data.remote.MoviesRemoteDataSource
import com.example.moviesapp.data.remote.MoviesRemoteDataSourceImpl
import com.example.moviesapp.data.repository.MoviesRepositoryImpl
import com.example.moviesapp.domain.model.LocalAgencyMovie
import com.example.moviesapp.domain.model.NetflixMovie
import com.example.moviesapp.domain.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoviesDatabase(app: Application): MovieDatabase {
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            "movies.db"
        ).fallbackToDestructiveMigration().build()

    }




    @Provides
    @Singleton
    fun provideMoviesDataSource():MoviesRemoteDataSource{
        return MoviesRemoteDataSourceImpl(
            baseUrl = Constants.BASE_REL
            )
    }

    @Provides
    @Singleton
    fun provideMoviesParser():CSVParser<LocalAgencyMovie>{
        return LocalAgencyMoviesParser()
    }

    @Provides
    @Singleton
    fun provideNetflixParser():CSVParser<NetflixMovie>{
        return NetflixMoviesParser()
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(moviesRemoteDataSource: MoviesRemoteDataSource,
                                localAgencyMoviesParser: LocalAgencyMoviesParser,
    db:MovieDatabase):MoviesRepository
    {
        return MoviesRepositoryImpl(moviesRemoteDataSource = moviesRemoteDataSource,
        localAgencyMoviesParser = localAgencyMoviesParser, db = db)
    }



}