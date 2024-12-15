package com.example.jokeapp

import android.app.Application
import com.example.jokeapp.di.AppComponent
import com.example.jokeapp.di.DaggerAppComponent

class JokeApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .application(this)  // Передаем application
            .build()
    }


}

