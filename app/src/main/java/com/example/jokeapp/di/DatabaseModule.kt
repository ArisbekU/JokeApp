package com.example.jokeapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room

import dagger.Module
import dagger.Provides
import data.database.JokeDao
import data.database.JokeDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun provideJokeDatabase(context: Context): JokeDatabase {
        return JokeDatabase.getInstance(context)
    }

    @Provides
    fun provideJokeDao(jokeDatabase: JokeDatabase): JokeDao {
        return jokeDatabase.jokeDao
    }
}
