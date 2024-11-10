package com.example.jokeapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jokeapp.databinding.FragmentJokeDetailBinding
import data.Joke

class JokeDetailFragment : Fragment() {

    private var _binding: FragmentJokeDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJokeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получение аргументов из Bundle
        val category = arguments?.getString(ARG_CATEGORY) ?: "No category"
        val question = arguments?.getString(ARG_QUESTION) ?: "No question"
        val answer = arguments?.getString(ARG_ANSWER) ?: "No answer"

        // Установка значений в TextView
        binding.categoryDetails.text = category
        binding.questionDetails.text = question
        binding.answerDetails.text = answer
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_CATEGORY = "ARG_CATEGORY"
        const val ARG_QUESTION = "ARG_QUESTION"
        const val ARG_ANSWER = "ARG_ANSWER"

        fun newInstance(joke: Joke): JokeDetailFragment {
            val fragment = JokeDetailFragment()
            val args = Bundle().apply {
                putString(ARG_CATEGORY, joke.category)
                putString(ARG_QUESTION, joke.question)
                putString(ARG_ANSWER, joke.answer)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
