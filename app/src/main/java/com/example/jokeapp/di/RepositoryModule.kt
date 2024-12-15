package com.example.jokeapp.di


import com.example.jokeapp.network.JokeApiService
import data.database.JokeDao
import domain.repository.JokeRepository
import dagger.Module
import dagger.Provides
import data.repisotory.JokeRepositoryImpl
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideJokeRepository(
        apiService: JokeApiService,
        jokeDao: JokeDao
    ): JokeRepository {
        return JokeRepositoryImpl(apiService, jokeDao)
    }
}
