package com.example.jokeapp

import data.Joke

interface JokeDetailView {
    fun showJokeInfo(joke: Joke)
    fun showErrorAndClosedInfo(errorMessage: String)
}