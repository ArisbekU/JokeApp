package com.example.jokeapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jokeapp.databinding.ActivityMainBinding
import data.Joke
import data.JokeAdapter
import com.example.jokeapp.R
import com.example.jokeapp.fragments.JokeDetailFragment
import com.example.jokeapp.fragments.JokeListFragment


class MainActivity : AppCompatActivity(), JokeListFragment.JokeListListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


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
