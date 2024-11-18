package com.example.jokeapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jokeapp.databinding.ActivityMainBinding
import data.Joke
import data.JokeAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var jokeAdapter: JokeAdapter
    private val jokeListViewModel: JokeListViewModel by lazy {
        ViewModelProvider(this).get(JokeListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        jokeAdapter = JokeAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = jokeAdapter

        // Подписка на изменения в списке шуток
        jokeListViewModel.jokes.observe(this, Observer { jokes ->
            if (jokes.isEmpty()) {
                binding.noJokesTextView.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.noJokesTextView.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                jokeAdapter.submitList(jokes)
            }
        })

        // Искусственная задержка
        binding.progressBar.visibility = View.VISIBLE
        jokeListViewModel.loadJokes()

        // Обработка нажатия кнопки "Добавить шутку"
        binding.addJokeButton.setOnClickListener {
            val intent = Intent(this, AddJokeActivity::class.java)
            startActivity(intent)
        }
    }
}
