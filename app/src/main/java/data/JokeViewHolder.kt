package data


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jokeapp.databinding.ItemJokeBinding

class JokeViewHolder(private val binding: ItemJokeBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(joke: Joke) {
        binding.jokeCategory.text = joke.category
        binding.jokeQuestion.text = joke.question
        binding.jokeAnswer.text = joke.answer

    }

    companion object {
        fun from(parent: ViewGroup): JokeViewHolder {
            val binding = ItemJokeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return JokeViewHolder(binding)
        }
    }
}