package presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jokeapp.network.ApiClient
import data.database.CachedJokeEntity
import data.database.JokeDatabase
import data.database.UserJokeEntity

import domain.model.Joke
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(private val database: JokeDatabase) : ViewModel() {

    private val _jokes = MutableLiveData<List<Joke>>()
    val jokes: LiveData<List<Joke>> = _jokes

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    var isLoading = false

    // Загружаем шутки из базы данных
    private fun loadJokesFromDatabase() {
        viewModelScope.launch {
            // Загружаем пользовательские шутки
            val userJokes = withContext(Dispatchers.IO) { database.jokeDao.getAllUserJokes() }
            // Загружаем кэшированные шутки
            val cachedJokes = withContext(Dispatchers.IO) { database.jokeDao.getAllCachedJokes() }

            // Объединяем два списка и конвертируем их в тип Joke
            val allJokes = mutableListOf<Joke>()

            // Добавляем пользовательские шутки
            allJokes.addAll(userJokes.map { joke ->
                Joke(
                    id = joke.id,
                    category = joke.category,
                    question = joke.question,
                    answer = joke.answer,
                    source = "user" // Источник: пользователь
                )
            })

            // Добавляем кэшированные шутки
            allJokes.addAll(cachedJokes.map { joke ->
                Joke(
                    id = joke.id,
                    category = joke.category,
                    question = joke.question,
                    answer = joke.answer,
                    source = "network" // Источник: сеть (кэш)
                )
            })

            // Обновляем LiveData
            _jokes.value = allJokes
        }
    }

    fun fetchJokesFromNetwork() {
        if (isLoading) return
        isLoading = true
        viewModelScope.launch {
            try {
                // Получаем данные с API
                val jokesFromNetwork = withContext(Dispatchers.IO) {
                    val response = ApiClient.api.getJokes()
                    // Преобразуем полученные шутки из API в CachedJokeEntity
                    response.jokes.map { joke ->
                        CachedJokeEntity(
                            id = joke.id,
                            category = joke.category,
                            question = joke.setup,
                            answer = joke.delivery,
                            timestamp = System.currentTimeMillis() // Записываем время получения
                        )
                    }
                }

                // Сохраняем шутки в таблицу временного кэша
                withContext(Dispatchers.IO) {
                    database.jokeDao.insertCachedJokes(jokesFromNetwork)
                }

                // Загружаем обновленный список шуток из базы данных
                loadJokesFromDatabase()

            } catch (e: Exception) {
                // Логируем ошибку
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }


    // Удаление устаревших шуток
    private fun clearOldJokes() {
        viewModelScope.launch(Dispatchers.IO) {
            val expiryTime = System.currentTimeMillis() - 24 * 60 * 60 * 1000 // 24 часа
            database.jokeDao.clearOldCachedJokes(expiryTime)
        }
    }
    // Метод для получения уникального ID для шутки
    fun getNextJokeId(): Int {
        return (_jokes.value?.size ?: 0) + 1
    }
    // Инициализация
    init {
        loadJokesFromDatabase()
        clearOldJokes()
    }
    // Добавление пользовательской шутки
    fun addUserJoke(joke: Joke) {
        viewModelScope.launch {
            val newUserJoke = UserJokeEntity(
                id = 0, // Автоматическая генерация ID
                category = joke.category,
                question = joke.question,
                answer = joke.answer,
                source = "user"
            )

            withContext(Dispatchers.IO) {
                database.jokeDao.insertUserJoke(newUserJoke)
            }

            // Обновляем список шуток
            loadJokesFromDatabase()
        }
    }
    fun handleError() {
        viewModelScope.launch {
            val cachedJokes = withContext(Dispatchers.IO) { database.jokeDao.getAllCachedJokes() }
            if (cachedJokes.isNotEmpty()) {
                _errorMessage.postValue("Ошибка загрузки. Показаны шутки из кэша.")
                loadJokesFromDatabase()
            } else {
                _errorMessage.postValue("Ошибка загрузки. Кэш пуст.")
            }
        }
    }
    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}

