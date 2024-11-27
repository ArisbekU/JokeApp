package com.example.jokeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jokeapp.network.ApiClient

import data.Joke
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel : ViewModel() {

    private val _jokes = MutableLiveData<List<Joke>>(emptyList())
    val jokes: LiveData<List<Joke>> get() = _jokes

    private var currentPage = 1

    var isLoading = false

    init {
        loadJokesWithDelay()
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
                Joke(1, "Category3", "What do you call a bear with no teeth?", "A gummy bear.","user"),
                Joke(2, "Category4", "Why can't your nose be 12 inches long?", "Because then it would be a foot.","user"),
                )
            _jokes.value = jokes
        }
    }

    fun fetchJokesFromNetwork() {

        if (isLoading) return // Предотвращаем повторный вызов, пока загружаются данные
        isLoading = true

        viewModelScope.launch {
            val newJokes = withContext(Dispatchers.IO) {
                try {
                    val response = ApiClient.api.getJokes()
                    response.jokes.map { joke ->
                        Joke(
                            id = joke.id,
                            category = joke.category,
                            question = joke.setup,
                            answer = joke.delivery,
                            source = "network"
                        )
                    }
                } catch (e: Exception) {
                    emptyList<Joke>()
                }
            }
            val currentList = _jokes.value.orEmpty().toMutableList()
            currentList.addAll(newJokes)
            _jokes.value = currentList

            isLoading = false
            currentPage++
        }
    }
}
