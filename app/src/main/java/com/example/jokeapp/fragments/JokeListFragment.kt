package com.example.jokeapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jokeapp.MainActivity
import com.example.jokeapp.MainActivityViewModel
import com.example.jokeapp.R
import com.example.jokeapp.databinding.FragmentJokeListBinding
import data.Joke
import data.JokeAdapter

class JokeListFragment : Fragment() {

    private var _binding: FragmentJokeListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainActivityViewModel by activityViewModels()
    private lateinit var adapter: JokeAdapter

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

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = JokeAdapter { joke -> listener?.onJokeSelected(joke) }
        binding.recyclerView.adapter = adapter

        // Индикатор загрузки
        binding.progressBar.visibility = View.VISIBLE

        viewModel.jokes.observe(viewLifecycleOwner) { jokes ->
            _binding?.let { binding ->
                binding.progressBar.visibility = View.GONE
                if (jokes.isEmpty()) {
                    binding.emptyTextView.visibility = View.VISIBLE
                } else {
                    binding.emptyTextView.visibility = View.GONE
                }
                adapter.submitList(jokes)
            }
        }

        viewModel.jokes.observe(viewLifecycleOwner) { jokes ->
            binding.progressBar.visibility = View.GONE
            if (jokes.isEmpty()) {
                binding.emptyTextView.visibility = View.VISIBLE
                binding.progressBar.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.emptyTextView.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
            adapter.submitList(jokes)
        }

        //viewModel.fetchJokesFromNetwork() // Загружаем шутки из сети

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                // Если пользователь дошёл до конца списка, подгружаем новые шутки
                if (!viewModel.isLoading && lastVisibleItemPosition + 1 >= totalItemCount) {
                    viewModel.fetchJokesFromNetwork() // Загружаем новые шутки
                }
            }
        })

        binding.emptyTextView.setOnClickListener {
            val addJokeFragment = AddJokeFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, addJokeFragment)
                .commit()
        }

        // Обработка клика по кнопке AddJoke
        binding.addJokeButton.setOnClickListener {
            // Открываем фрагмент добавления шутки
            val addJokeFragment = AddJokeFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, addJokeFragment)
                .addToBackStack(null)
                .commit()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

