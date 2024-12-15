package domain.usecase

import domain.model.Joke
import domain.repository.JokeRepository
import javax.inject.Inject

class FetchJokesUseCase @Inject constructor(private val repository: JokeRepository)  {
    suspend operator fun invoke(): List<Joke> = repository.fetchJokesFromNetwork()
}

class SaveCachedJokesUseCase @Inject constructor(private val repository: JokeRepository) {
    suspend operator fun invoke(jokes: List<Joke>) = repository.saveCachedJokes(jokes)
}

class GetCachedJokesUseCase @Inject constructor(private val repository: JokeRepository) {
    suspend operator fun invoke(expiryTime: Long): List<Joke> = repository.getCachedJokes(expiryTime)
}

class AddUserJokeUseCase @Inject constructor(private val repository: JokeRepository) {
    suspend operator fun invoke(joke: Joke) = repository.addUserJoke(joke)
}
class GetUserJokesFromDatabaseUseCase @Inject constructor(private val jokeRepository: JokeRepository) {

    // Метод для получения всех пользовательских шуток из базы данных
    suspend operator fun invoke(): List<Joke> {
        return jokeRepository.getUserJokes()
    }
}