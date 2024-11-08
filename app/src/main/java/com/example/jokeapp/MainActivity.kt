package com.example.jokeapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jokeapp.databinding.ActivityMainBinding
import data.Joke
import data.JokeAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val jokesList = listOf(
            Joke("Игроки в компьютерные игры", "Что сделает дотер, если у него плохой день?", "Зайдёт в игру и обвинит команду за всё, что пошло не так!"),
            Joke("Инопланетяне", "Почему инопланетянам не слышат нас?", "Потому что они находятся на другой волне"),
            Joke("Таксисты", "Почему таксистам не нужен навигатор", "Чтобы пассажир не понял что он ездит самым длинным маршрутом"),
            Joke("Художники", "Почему художник не ходит на работу?", "Потому что он работает дома"),
            Joke("Учителя", "Почему учителя всегда носят с собой красные ручки?", "Чтобы поставить двойку ученику"),
            Joke("Театр", "Почему актеры не ходят в театр?", "Потому что жизнь - это театр. А люди в них - актеры"),
            Joke("Футболисты", "Почему футболист боится пустого холодильника?", "Потому что это напоминает ему о пустых воротах")

        )

        val adapter = JokeAdapter { joke ->
            val intent = Intent(this, JokeDetail::class.java).apply {
                putExtra("CATEGORY", joke.category)
                putExtra("QUESTION", joke.question)
                putExtra("ANSWER", joke.answer)
            }
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        adapter.submitList(jokesList)
    }
}