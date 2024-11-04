package com.example.jokeapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import data.Joke
import data.JokeAdapter

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Список шуток
        val jokes = listOf(
            Joke("Игроки в компьютерные игры", "Что сделает дотер, если у него плохой день?", "Зайдёт в игру и обвинит команду за всё, что пошло не так!"),
            Joke("Инопланетяне", "Почему инопланетянам не слышат нас?", "Потому что они находятся на другой волне"),
            Joke("Таксисты", "Почему таксистам не нужен навигатор", "Чтобы пассажир не понял что он ездит самым длинным маршрутом"),
            Joke("Художники", "Почему художник не ходит на работу?", "Потому что он работает дома"),
            Joke("Учителя", "Почему учителя всегда носят с собой красные ручки?", "Чтобы поставить двойку ученику"),
            Joke("Театр", "Почему актеры не ходят в театр?", "Потому что жизнь - это театр. А люди в них - актеры"),
            Joke("Футболисты", "Почему футболист боится пустого холодильника?", "Потому что это напоминает ему о пустых воротах")
        )

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = JokeAdapter(jokes) { joke ->
            val intent = Intent(this, JokeDetail::class.java).apply {
                putExtra("category", joke.category)
                putExtra("question", joke.question)
                putExtra("answer", joke.answer)
            }
            startActivity(intent)
        }
    }
}