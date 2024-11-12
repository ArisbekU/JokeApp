package com.example.jokeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.jokeapp.databinding.ActivityJokeDetailBinding
import data.Joke
import kotlin.jvm.Throws

class JokeDetail : AppCompatActivity(), JokeDetailView {
    private lateinit var binding: ActivityJokeDetailBinding
    private lateinit var presenter: JokeDetailPresenter

    companion object{
        const val ARG_CATEGORY = "JokeDetail.ARG_CATEGORY"
        const val ARG_QUESTION = "JokeDetail.ARG_QUESTION"
        const val ARG_ANSWER = "JokeDetail.ARG_ANSWER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJokeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = JokeDetailPresenter(this)

        val category = intent.getStringExtra(ARG_CATEGORY) ?: "No category"
        val question = intent.getStringExtra(ARG_QUESTION) ?: "No question"
        val answer = intent.getStringExtra(ARG_ANSWER) ?: "No answer"
        presenter.setJokeDetails(category, question, answer)
        //val joke = Joke(category, question, answer)
        //showJokeInfo(joke)

    }

    override fun showJokeDetails(joke: Joke) {
        with(binding){
            categoryDetails.text = joke.category
            questionDetails.text = joke.question
            answerDetails.text = joke.answer
        }
    }

    override fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        finish()
    }
}