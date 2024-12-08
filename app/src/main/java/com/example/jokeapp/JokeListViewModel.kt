package com.example.jokeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import domain.model.Joke
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class JokeListViewModel : ViewModel() {

    private val _jokes = MutableLiveData<List<Joke>>()
    val jokes: LiveData<List<Joke>> get() = _jokes

    private val job = Job()  // Job для отслеживания работы корутин
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)  // Создаем корутинный scope, с привязкой к главному потоку

    private val jokesList = mutableListOf<Joke>()

    // Асинхронная загрузка шуток с задержкой
    fun loadJokes() {
        val jokesList = listOf(
            Joke(1,"Игроки в компьютерные игры", "Что сделает дотер, если у него плохой день?", "Зайдёт в игру и обвинит команду за всё, что пошло не так!", "user"),
            Joke(1,"Инопланетяне", "Почему инопланетянам не слышат нас?", "Потому что они находятся на другой волне","user")
        )
        _jokes.postValue(jokesList)
    }

    // Добавление новой шутки в список
    fun addJoke(joke: Joke) {
        jokesList.add(joke)
        loadJokes()  // Перезагружаем список после добавления новой шутки
    }
    override fun onCleared() {
        super.onCleared()
        job.cancel() // Отменяем все корутины, связанные с этим Job
    }
}
