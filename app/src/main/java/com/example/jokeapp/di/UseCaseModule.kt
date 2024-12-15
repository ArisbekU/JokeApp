package com.example.jokeapp.di

import dagger.Module
import dagger.Provides
import domain.repository.JokeRepository
import domain.usecase.AddUserJokeUseCase
import domain.usecase.FetchJokesUseCase
import domain.usecase.GetCachedJokesUseCase
import domain.usecase.GetUserJokesFromDatabaseUseCase
import domain.usecase.SaveCachedJokesUseCase

@Module
class UseCaseModule {

    @Provides
    fun provideFetchJokesUseCase(repository: JokeRepository): FetchJokesUseCase {
        return FetchJokesUseCase(repository)
    }

    @Provides
    fun provideSaveCachedJokesUseCase(repository: JokeRepository): SaveCachedJokesUseCase {
        return SaveCachedJokesUseCase(repository)
    }

    @Provides
    fun provideGetCachedJokesUseCase(repository: JokeRepository): GetCachedJokesUseCase {
        return GetCachedJokesUseCase(repository)
    }

    @Provides
    fun provideAddUserJokeUseCase(repository: JokeRepository): AddUserJokeUseCase {
        return AddUserJokeUseCase(repository)
    }

    @Provides
    fun provideGetUserJokesFromDatabaseUseCase(repository: JokeRepository): GetUserJokesFromDatabaseUseCase {
        return GetUserJokesFromDatabaseUseCase(repository)
    }
}

