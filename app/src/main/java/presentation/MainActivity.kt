package presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import presentation.viewmodel.*
import com.example.jokeapp.R
import com.example.jokeapp.databinding.ActivityMainBinding
import com.example.jokeapp.network.ApiClient
import presentation.ui.JokeListFragment
import data.repository.JokeRepositoryImpl
import data.database.JokeDatabase
import domain.model.Joke
import domain.usecase.*
import presentation.ui.JokeDetailFragment

class MainActivity : AppCompatActivity(), JokeListFragment.JokeListListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Создаем экземпляр базы данных
        val database = JokeDatabase.getInstance(this)

        // Создайте или получите JokeApiService, если он используется
        val apiService = ApiClient.api// Пример создания через Retrofit или другой метод

        // Создаем экземпляр репозитория с необходимыми зависимостями
        val repository = JokeRepositoryImpl(apiService, database.jokeDao)

        // Создаем Use Cases
        val fetchJokesUseCase = FetchJokesUseCase(repository)
        val saveCachedJokesUseCase = SaveCachedJokesUseCase(repository)
        val getCachedJokesUseCase = GetCachedJokesUseCase(repository)
        val addUserJokeUseCase = AddUserJokeUseCase(repository)
        val getUserJokesFromDatabaseUseCase = GetUserJokesFromDatabaseUseCase(repository)

        // Создаем ViewModel через фабрику
        val factory = MainActivityViewModelFactory(
            fetchJokesUseCase,
            saveCachedJokesUseCase,
            getCachedJokesUseCase,
            addUserJokeUseCase,
            getUserJokesFromDatabaseUseCase
        )

        viewModel = ViewModelProvider(this, factory).get(MainActivityViewModel::class.java)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, JokeListFragment())
                .commit()
        }
    }

    override fun onJokeSelected(joke: Joke) {
        val detailFragment = JokeDetailFragment.newInstance(joke)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, detailFragment)
            .addToBackStack(null)
            .commit()
    }
}

