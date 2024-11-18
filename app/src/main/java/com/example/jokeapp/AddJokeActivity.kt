package com.example.jokeapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.jokeapp.databinding.ActivityAddJokeBinding
import data.Joke
import java.util.UUID

class AddJokeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddJokeBinding
    private val jokeListViewModel: JokeListViewModel by lazy {
        ViewModelProvider(this).get(JokeListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddJokeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addJokeButton.setOnClickListener {
            val category = binding.categoryEditText.text.toString()
            val question = binding.questionEditText.text.toString()
            val answer = binding.answerEditText.text.toString()

            if (category.isNotEmpty() && question.isNotEmpty() && answer.isNotEmpty()) {
                val joke = Joke(id = UUID.randomUUID().toString(), category = category, question = question, answer = answer)
                jokeListViewModel.addJoke(joke)
                Toast.makeText(this, "Шутка добавлена!", Toast.LENGTH_SHORT).show()
                finish()  // Закрыть активность и вернуться на главный экран
            } else {
                Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

