package com.example.jokeapp

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jokeapp.fragments.AddJokeFragment
import data.Joke
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel : ViewModel() {

    // Изменим MutableLiveData на LiveData для правильного типа
    private val _jokes = MutableLiveData<List<Joke>>(emptyList())
    val jokes: LiveData<List<Joke>> get() = _jokes
    init {
        loadInitialJokes()

    }

    private fun loadInitialJokes() {
        viewModelScope.launch {
            val initialJokes = withContext(Dispatchers.IO) {
                listOf(
                    Joke("1", "Category1", "Why did the chicken cross the road?", "To get to the other side."),
                    Joke("2", "Category2", "Why was the math book sad?", "Because it had too many problems.")
                )
            }
            _jokes.value = initialJokes
        }
    }

    // Метод для добавления шутки
    fun addJoke(joke: Joke) {
        viewModelScope.launch {
            val updatedList = withContext(Dispatchers.Default) {
                val currentList = _jokes.value.orEmpty().toMutableList()
                currentList.add(joke)
                currentList
            }
            _jokes.value = updatedList
        }
    }

    // Метод для получения уникального ID для шутки
    fun getNextJokeId(): Int {
        return (_jokes.value?.size ?: 0) + 1
    }

    // Загружаем шутки с задержкой
    fun loadJokesWithDelay() {
        viewModelScope.launch {
            _jokes.value = emptyList() // Показываем пустой список (загрузка)
            withContext(Dispatchers.IO) {
                kotlinx.coroutines.delay(2000) // Задержка 2 секунды
            }
            val jokes = listOf(
                Joke("3", "Category3", "What do you call a bear with no teeth?", "A gummy bear."),
                Joke("4", "Category4", "Why can't your nose be 12 inches long?", "Because then it would be a foot.")
            )
            _jokes.value = jokes
        }
    }
}

