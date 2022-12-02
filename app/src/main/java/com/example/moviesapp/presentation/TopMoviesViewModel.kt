package com.example.moviesapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.common.Resource
import com.example.moviesapp.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.collect

@HiltViewModel
class TopMoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {


    fun getTopMovies(){
        viewModelScope.launch {
            moviesRepository.getTopMovies(true).collect{result ->
                when(result){
                    is Resource.Error -> Log.d("AhmedTopMovesError","Error")
                    is Resource.Loading ->{
                        Log.d("AhmedTopMovesLoading","Loading")
                    }
                    is Resource.Success -> {
                        Log.d("AhmedTopMovesSuccess", result.data?.topItems?.first().toString())
                        Log.d("AhmedTopMovesSuccess", result.data?.topItems?.size.toString())
                    }
                }
            }
        }
    }

    fun getTopTVs(){
        viewModelScope.launch {
            moviesRepository.getTopTvs(true).collect{result ->
                when(result){
                    is Resource.Error -> Log.d("AhmedTopTvsError","Error")
                    is Resource.Loading ->{
                        Log.d("AhmedTopTvsLoading","Loading")
                    }
                    is Resource.Success -> {
                        Log.d("AhmedTopTvsSuccess", result.data?.topItems?.first().toString())
                        Log.d("AhmedTopTvsSuccess", result.data?.topItems?.size.toString())
                    }
                }
            }
        }
    }

    fun getLocalAgencyMovies(){
        viewModelScope.launch {
            moviesRepository.getLocalAgencyMovies().collect {
                when(it){
                    is Resource.Error -> Log.d("AhmedLAgencyError","Error")
                    is Resource.Loading -> Log.d("AhmedLAgencyLoading","Loading")
                    is Resource.Success -> {
                        Log.d("AhmedLAgencySuccess", it.data?.first().toString())
                        Log.d("AhmedLAgencySuccess",it.data?.size.toString())
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getNetflixMovies(){
        viewModelScope.launch {
            moviesRepository.getNetflixData().collect { result ->
                when(result){
                    is Resource.Error -> Log.d("AhmedNFError","Error")
                    is Resource.Loading -> Log.d("AhmedNFLoading","Loading")
                    is Resource.Success -> Log.d("AhmedNFSuccess",result.data.toString())
                }
            }
            }
        }
 }