package com.example.jokeapp.di

import android.app.Application
import android.content.Context
import com.example.jokeapp.JokeApp
import com.example.jokeapp.presentation.ui.AddJokeFragment
import dagger.BindsInstance
import data.repository.JokeRepositoryImpl
import dagger.Component
import presentation.MainActivity
import presentation.ui.JokeDetailFragment
import presentation.ui.JokeListFragment
import presentation.viewmodel.MainActivityViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class, RepositoryModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
    fun inject(application: JokeApp)
    fun inject(mainActivity: MainActivity)
    fun inject(jokeListFragment: JokeListFragment)
    fun inject(jokeDetailFragment: JokeDetailFragment)
    fun inject(mainActivityViewModel: MainActivityViewModel)
    fun inject(addJokeFragment: AddJokeFragment)
}
