package com.example.jokeapp

import data.Joke

interface JokeDetailView {
    fun showJokeDetails(joke: Joke)
    fun showError(errorMessage: String)
}