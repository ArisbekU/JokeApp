package com.example.jokeapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.jokeapp.databinding.FragmentAddJokeBinding
import com.example.jokeapp.MainActivityViewModel
import data.Joke

class AddJokeFragment : Fragment() {

    private var _binding: FragmentAddJokeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddJokeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addJokeButton.setOnClickListener {
            // Получаем введенные данные
            val category = binding.categoryInput.text.toString().trim()
            val question = binding.questionInput.text.toString().trim()
            val answer = binding.answerInput.text.toString().trim()

            // Проверяем, что все поля заполнены
            if (category.isEmpty() || question.isEmpty() || answer.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Создаем объект Joke
                val joke = Joke(
                    id = viewModel.getNextJokeId(),
                    category = category,
                    question = question,
                    answer = answer,
                    source = "user"  // Указываем источник, например, "user"
                )

                // Добавляем шутку в ViewModel
                viewModel.addUserJoke(joke)

                // Показываем сообщение об успешном добавлении
                Toast.makeText(requireContext(), "Joke added!", Toast.LENGTH_SHORT).show()

                // Возвращаемся на предыдущий экран
                parentFragmentManager.popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



