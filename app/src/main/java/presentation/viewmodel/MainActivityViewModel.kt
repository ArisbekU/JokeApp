package presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import domain.model.Joke
import domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(
    private val fetchJokesUseCase: FetchJokesUseCase,
    private val saveCachedJokesUseCase: SaveCachedJokesUseCase,
    private val getCachedJokesUseCase: GetCachedJokesUseCase,
    private val addUserJokeUseCase: AddUserJokeUseCase,
    private val getUserJokesFromDatabaseUseCase: GetUserJokesFromDatabaseUseCase // UseCase для получения пользовательских шуток
) : ViewModel() {

    private val _jokes = MutableLiveData<List<Joke>>()
    val jokes: LiveData<List<Joke>> = _jokes

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    var isLoading = false

    // Загружаем шутки из базы данных (пользовательские + кэшированные)
    private fun loadJokesFromDatabase() {
        viewModelScope.launch {
            try {
                // Загружаем пользовательские шутки
                val userJokes = withContext(Dispatchers.IO) {
                    getUserJokesFromDatabaseUseCase.invoke() // Ваш use case для получения пользовательских шуток
                }

                // Загружаем кэшированные шутки за последние 24 часа
                val jokesFromCache = withContext(Dispatchers.IO) {
                    getCachedJokesUseCase.invoke(System.currentTimeMillis() - 24 * 60 * 60 * 1000) // За последние 24 часа
                }

                // Объединяем два списка шуток
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
                allJokes.addAll(jokesFromCache.map { joke ->
                    Joke(
                        id = joke.id,
                        category = joke.category,
                        question = joke.question,
                        answer = joke.answer,
                        source = "network" // Источник: сеть (кэш)
                    )
                })

                // Обновляем LiveData с объединенным списком шуток
                _jokes.value = allJokes
                Log.d("MainActivityViewModel", "Jokes loaded: ${allJokes.size}")

            } catch (e: Exception) {
                Log.e("MainActivityViewModel", "Error loading jokes", e)
            }
        }
    }

    // Получаем шутки из сети
    fun fetchJokesFromNetwork() {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            try {
                val jokesFromNetwork = withContext(Dispatchers.IO) { fetchJokesUseCase() }

                // Сохраняем шутки в кэш
                withContext(Dispatchers.IO) { saveCachedJokesUseCase(jokesFromNetwork) }

                loadJokesFromDatabase()
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.postValue("Ошибка загрузки. Проверьте подключение к интернету.")
            } finally {
                isLoading = false
            }
        }
    }

    // Добавление пользовательской шутки
    fun addUserJoke(joke: Joke) {
        viewModelScope.launch {
            try {
                // Сохраняем шутку в базу данных
                addUserJokeUseCase.invoke(joke)
                Log.d("MainActivityViewModel", "Joke saved: $joke")
                // Загружаем обновленные шутки из базы данных
                loadJokesFromDatabase()
            } catch (e: Exception) {
                Log.e("MainActivityViewModel", "Error saving joke", e)
            }
        }
    }

    // Метод для получения уникального ID для шутки
    fun getNextJokeId(): Int {
        return (_jokes.value?.size ?: 0) + 1
    }

    // Обработка ошибок загрузки
    fun handleError() {
        viewModelScope.launch {
            loadJokesFromDatabase()
            _errorMessage.postValue("Ошибка загрузки. Показаны шутки из кэша..")
        }
    }

    // Очистка сообщения об ошибке
    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    // Инициализация
    init {
        loadJokesFromDatabase()
    }
}
