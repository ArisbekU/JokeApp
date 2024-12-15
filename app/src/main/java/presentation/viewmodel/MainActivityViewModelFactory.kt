package presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import domain.usecase.*

class MainActivityViewModelFactory(
    private val fetchJokesUseCase: FetchJokesUseCase,
    private val saveCachedJokesUseCase: SaveCachedJokesUseCase,
    private val getCachedJokesUseCase: GetCachedJokesUseCase,
    private val addUserJokeUseCase: AddUserJokeUseCase,
    private val getUserJokesFromDatabaseUseCase: GetUserJokesFromDatabaseUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel(
                fetchJokesUseCase,
                saveCachedJokesUseCase,
                getCachedJokesUseCase,
                addUserJokeUseCase,
                getUserJokesFromDatabaseUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
