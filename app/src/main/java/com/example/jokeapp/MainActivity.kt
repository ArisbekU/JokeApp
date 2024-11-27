package com.example.jokeapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jokeapp.databinding.ActivityMainBinding
import data.Joke
import data.JokeAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = JokeAdapter { joke -> openJokeDetail(joke) }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.jokes.observe(this) { jokes ->
            // Обновление данных в адаптере
            adapter.submitList(jokes)
        }
    }
    private fun openJokeDetail(joke: Joke) {

        val intent = Intent(this, JokeDetail::class.java).apply {
            putExtra(JokeDetail.ARG_CATEGORY, joke.category)
            putExtra(JokeDetail.ARG_QUESTION, joke.question)
            putExtra(JokeDetail.ARG_ANSWER, joke.answer)
        }
        startActivity(intent)
    }
}