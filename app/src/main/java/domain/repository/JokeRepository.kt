package domain.repository

import domain.model.Joke

interface JokeRepository {
    suspend fun fetchJokesFromNetwork(): List<Joke>
    suspend fun saveCachedJokes(jokes: List<Joke>)
    suspend fun getCachedJokes(expiryTime: Long): List<Joke>
    suspend fun addUserJoke(joke: Joke)
    suspend fun getUserJokes(): List<Joke>
}