package com.example.jokeapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.jokeapp.databinding.ActivityMainBinding
import com.example.jokeapp.fragments.AddJokeFragment
import com.example.jokeapp.fragments.JokeListFragment
import com.example.jokeapp.fragments.JokeDetailFragment
import data.Joke



class MainActivity : AppCompatActivity(), JokeListFragment.JokeListListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

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

