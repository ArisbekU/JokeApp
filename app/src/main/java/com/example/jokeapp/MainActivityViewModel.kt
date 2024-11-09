package com.example.jokeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import data.Joke

class MainActivityViewModel: ViewModel() {
    private val _jokes = MutableLiveData<List<Joke>>()
    val jokes: LiveData<List<Joke>> get() = _jokes

    init {
        // Инициализация списка шуток (может быть заменено логикой загрузки данных)
        loadJokes()
    }

    private fun loadJokes() {
        // Загрузка статичного списка шуток (вместо этого может быть запрос к базе данных или API)
        val jokesList = listOf(
            Joke("Игроки в компьютерные игры", "Что сделает дотер, если у него плохой день?", "Зайдёт в игру и обвинит команду за всё, что пошло не так!"),
            Joke("Инопланетяне", "Почему инопланетянам не слышат нас?", "Потому что они находятся на другой волне"),
            Joke("Таксисты", "Почему таксистам не нужен навигатор", "Чтобы пассажир не понял что он ездит самым длинным маршрутом"),
            Joke("Художники", "Почему художник не ходит на работу?", "Потому что он работает дома"),
            Joke("Учителя", "Почему учителя всегда носят с собой красные ручки?", "Чтобы поставить двойку ученику"),
            Joke("Театр", "Почему актеры не ходят в театр?", "Потому что жизнь - это театр. А люди в них - актеры"),
            Joke("Футболисты", "Почему футболист боится пустого холодильника?", "Потому что это напоминает ему о пустых воротах")

        )
        _jokes.value = jokesList
    }

    fun addJoke(joke: Joke) {
        // Добавление новой шутки к текущему списку
        val updatedJokes = _jokes.value?.toMutableList() ?: mutableListOf()
        updatedJokes.add(joke)
        _jokes.value = updatedJokes
    }
}