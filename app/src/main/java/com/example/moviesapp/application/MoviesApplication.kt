package com.example.moviesapp.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoviesApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        context =this
    }

    companion object {
        lateinit var context: MoviesApplication
            private set
    }
}