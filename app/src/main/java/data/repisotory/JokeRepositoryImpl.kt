package data.repisotory

import data.database.UserJokeEntity
import com.example.jokeapp.network.JokeApiService
import data.database.CachedJokeEntity
import data.database.JokeDao
import domain.model.Joke
import domain.repository.JokeRepository

class JokeRepositoryImpl(
    private val apiService: JokeApiService,
    private val jokeDao: JokeDao
) : JokeRepository {
    override suspend fun fetchJokesFromNetwork(): List<Joke> {
        val response = apiService.getJokes()
        return response.jokes?.map { joke ->
            Joke(joke.id, joke.category, joke.setup, joke.delivery, "network")
        } ?: emptyList() // Возвращает пустой список, если `jokes` равно `null`
    }

    override suspend fun saveCachedJokes(jokes: List<Joke>) {
        val entities = jokes.map {
            CachedJokeEntity(it.id, it.category, it.question, it.answer, System.currentTimeMillis())
        }
        jokeDao.insertCachedJokes(entities)
    }

    override suspend fun getCachedJokes(expiryTime: Long): List<Joke> {
        return jokeDao.getAllCachedJokes().map {
            Joke(it.id, it.category, it.question, it.answer, "cache")
        }
    }

    override suspend fun addUserJoke(joke: Joke) {
        val entity = UserJokeEntity(id = joke.id, category = joke.category, question = joke.question, answer = joke.answer, source = "user")
        jokeDao.insertUserJoke(entity)
    }
}