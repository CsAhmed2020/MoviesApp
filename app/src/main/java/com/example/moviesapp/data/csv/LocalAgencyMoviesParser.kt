package com.example.moviesapp.data.csv


import com.example.moviesapp.domain.model.LocalAgencyMovie
import com.example.moviesapp.domain.model.NetflixMovie
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalAgencyMoviesParser @Inject constructor(): CSVParser<LocalAgencyMovie> {

    override suspend fun parse(stream: InputStream): List<LocalAgencyMovie> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val title = line.getOrNull(1).toString()
                    val releaseDate = line.getOrNull(2).toString()
                    val overview = line.getOrNull(3).toString()
                    val popularity = line.getOrNull(4).toString()
                    val voteAverage = line.getOrNull(5).toString()
                    val voteCount = line.getOrNull(6).toString()
                    val video = line.getOrNull(7).toString()
                    LocalAgencyMovie(
                        title = title,
                        releaseDate = releaseDate,
                        overview = overview,
                        popularity = popularity,
                        voteAverage = voteAverage,
                        voteCount = voteCount,
                        video = video,
                    )
                }
                .also {
                    csvReader.close()
                }
        }
    }
}

@Singleton
class NetflixMoviesParser @Inject constructor(): CSVParser<NetflixMovie>{
    override suspend fun parse(stream: InputStream): List<NetflixMovie> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val title = line.getOrNull(0).toString()
                    val type = line.getOrNull(1).toString()
                    val description = line.getOrNull(2).toString()
                    val year = line.getOrNull(3).toString()
                    val ageCertification = line.getOrNull(4).toString()
                    val runtime = line.getOrNull(5).toString()
                    val genres = line.getOrNull(6).toString()
                    val countries = line.getOrNull(7).toString()
                    val seasons = line.getOrNull(8).toString()
                    val imdbScore = line.getOrNull(9).toString()
                    val imdbVotes = line.getOrNull(10).toString()
                    val tmdbPopularity = line.getOrNull(11).toString()
                    val tmdbScore = line.getOrNull(12).toString()
                    NetflixMovie(
                        title = title,
                        type = type,
                        description = description,
                        releaseYear = year,
                        ageCertification = ageCertification,
                        runtime = runtime,
                        genres = genres,
                        productionCountries = countries,
                        seasons = seasons,
                        imdbScore = imdbScore,
                        imdbVotes = imdbVotes,
                        tmdbPopularity = tmdbPopularity,
                        tmdbScore = tmdbScore,
                    )
                }
                .also {
                    csvReader.close()
                }
        }
    }

}