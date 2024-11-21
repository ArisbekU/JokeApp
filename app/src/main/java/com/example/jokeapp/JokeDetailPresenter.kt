package com.example.jokeapp

import data.Joke

class JokeDetailPresenter(private val view: JokeDetailView){
    fun setJokeDetails(joke: Joke?) {
        // Проверяем, если объект joke равен null
        if (joke == null) {
            view.showErrorAndClosedInfo("Error: No joke details provided.")
            return
        }

        // Передаем объект joke в View
        view.showJokeInfo(joke)
    }
}

