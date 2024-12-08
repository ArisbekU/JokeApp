package com.example.jokeapp

import domain.model.Joke

interface JokeDetailView {
    fun showJokeDetails(joke: Joke)
    fun showError(errorMessage: String)
}