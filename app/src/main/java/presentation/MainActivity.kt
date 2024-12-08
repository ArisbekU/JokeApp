package presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import presentation.viewmodel.MainActivityViewModel
import presentation.viewmodel.MainActivityViewModelFactory
import com.example.jokeapp.R
import com.example.jokeapp.databinding.ActivityMainBinding
import presentation.ui.JokeListFragment
import presentation.ui.JokeDetailFragment
import data.database.JokeDatabase
import domain.model.Joke



class MainActivity : AppCompatActivity(), JokeListFragment.JokeListListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Получите экземпляр базы данных
        val database = JokeDatabase.getInstance(this)

        // Используйте фабрику для создания ViewModel
        val factory = MainActivityViewModelFactory(database)
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

