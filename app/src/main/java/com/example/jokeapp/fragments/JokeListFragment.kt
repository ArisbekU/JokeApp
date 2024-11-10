package com.example.jokeapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jokeapp.MainActivity
import com.example.jokeapp.MainActivityViewModel
import com.example.jokeapp.databinding.FragmentJokeListBinding
import data.Joke
import data.JokeAdapter

class JokeListFragment : Fragment() {

    private var _binding: FragmentJokeListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainActivityViewModel by activityViewModels()

    private lateinit var adapter: JokeAdapter

    // Интерфейс для взаимодействия с Activity
    interface JokeListListener {
        fun onJokeSelected(joke: Joke)
    }

    private var listener: JokeListListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is JokeListListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement JokeListListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJokeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = JokeAdapter { joke ->
            listener?.onJokeSelected(joke)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.jokes.observe(viewLifecycleOwner) { jokes ->
            adapter.submitList(jokes)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
