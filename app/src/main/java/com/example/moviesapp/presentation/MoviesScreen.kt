package com.example.moviesapp.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun MoviesScreen(moviesViewModel: TopMoviesViewModel = hiltViewModel()){

    /**
     *  register here https://imdb-api.com/api to get your api key
     *  see results in logcat using filter "Ahmed"
     *  */

    /** api data */
    moviesViewModel.getTopMovies()

    moviesViewModel.getTopTVs()

    /** csv file data */
    //moviesViewModel.getLocalAgencyMovies()

    /** Excel file data */
    //moviesViewModel.getNetflixMovies()

}