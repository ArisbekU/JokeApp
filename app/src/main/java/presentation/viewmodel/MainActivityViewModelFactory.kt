package presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import data.database.JokeDatabase

class MainActivityViewModelFactory(
    private val database: JokeDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
