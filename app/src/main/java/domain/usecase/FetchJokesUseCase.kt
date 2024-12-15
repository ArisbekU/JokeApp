package domain.usecase

import domain.model.Joke
import domain.repository.JokeRepository

class FetchJokesUseCase(private val repository: JokeRepository) {
    suspend operator fun invoke(): List<Joke> = repository.fetchJokesFromNetwork()
}

class SaveCachedJokesUseCase(private val repository: JokeRepository) {
    suspend operator fun invoke(jokes: List<Joke>) = repository.saveCachedJokes(jokes)
}

class GetCachedJokesUseCase(private val repository: JokeRepository) {
    suspend operator fun invoke(expiryTime: Long): List<Joke> = repository.getCachedJokes(expiryTime)
}

class AddUserJokeUseCase(private val repository: JokeRepository) {
    suspend operator fun invoke(joke: Joke) = repository.addUserJoke(joke)
}
class GetUserJokesFromDatabaseUseCase(private val jokeRepository: JokeRepository) {

    // Метод для получения всех пользовательских шуток из базы данных
    suspend operator fun invoke(): List<Joke> {
        return jokeRepository.getUserJokes()
    }
}