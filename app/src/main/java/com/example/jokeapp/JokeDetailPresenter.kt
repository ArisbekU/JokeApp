package com.example.jokeapp

import domain.model.Joke

class okeDetailPresenter(private val view: JokeDetailView){
    fun setJokeDetails(category: String?, question: String?, answer: String?) {

        if (category.isNullOrBlank() || question.isNullOrBlank() || answer.isNullOrBlank()) {
            view.showError("Invalid joke data")
        } else {
            val joke = Joke(1, category, question, answer,"user")
            view.showJokeDetails(joke)
        }
    }
}

