package com.example.jokeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import data.Joke
import java.util.UUID

class MainActivityViewModel: ViewModel() {
    private val _jokes = MutableLiveData<List<Joke>>()
    val jokes: LiveData<List<Joke>> get() = _jokes

    init {

        loadJokes()
    }

    private fun loadJokes() {

        val jokesList = listOf(
            Joke(UUID.randomUUID().toString(),"Игроки в компьютерные игры", "Что сделает дотер, если у него плохой день?", "Зайдёт в игру и обвинит команду за всё, что пошло не так!"),
            Joke(UUID.randomUUID().toString(),"Инопланетяне", "Почему инопланетянам не слышат нас?", "Потому что они находятся на другой волне"),
            Joke(UUID.randomUUID().toString(),"Таксисты", "Почему таксистам не нужен навигатор", "Чтобы пассажир не понял что он ездит самым длинным маршрутом"),
            Joke(UUID.randomUUID().toString(),"Художники", "Почему художник не ходит на работу?", "Потому что он работает дома"),
            Joke(UUID.randomUUID().toString(),"Учителя", "Почему учителя всегда носят с собой красные ручки?", "Чтобы поставить двойку ученику"),
            Joke(UUID.randomUUID().toString(),"Театр", "Почему актеры не ходят в театр?", "Потому что жизнь - это театр. А люди в них - актеры"),
            Joke(UUID.randomUUID().toString(),"Футболисты", "Почему футболист боится пустого холодильника?", "Потому что это напоминает ему о пустых воротах")

        )
        _jokes.value = jokesList
    }

    fun addJoke(newJoke: Joke) {

    }

}