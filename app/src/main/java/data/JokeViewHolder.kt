package data


import androidx.recyclerview.widget.RecyclerView
import com.example.jokeapp.databinding.ItemJokeBinding

class JokeViewHolder(
    private val binding: ItemJokeBinding,
    private val onClick: (Joke) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var currentJoke: Joke

    init {
        itemView.setOnClickListener {
            if (::currentJoke.isInitialized) {
                onClick(currentJoke)
            }
        }
    }

    fun bind(joke: Joke) {
        currentJoke = joke
        binding.jokeCategory.text = joke.category
        binding.jokeQuestion.text = joke.question
        binding.jokeAnswer.text = joke.answer
    }
}