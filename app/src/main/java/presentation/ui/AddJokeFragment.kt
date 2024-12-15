package com.example.jokeapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jokeapp.databinding.FragmentAddJokeBinding
import com.example.jokeapp.JokeApp
import domain.repository.JokeRepository
import javax.inject.Inject

class AddJokeFragment : Fragment() {

    private var _binding: FragmentAddJokeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var jokeRepository: JokeRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddJokeBinding.inflate(inflater, container, false)
        (requireActivity().application as JokeApp).appComponent.inject(this)  // Inject dependencies
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Now you can use jokeRepository
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
