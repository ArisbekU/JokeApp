package data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.jokeapp.databinding.ItemJokeBinding
import data.util.JokeDiffUtilCallback


class JokeAdapter(private val onClick: (Joke) -> Unit) :
    ListAdapter<Joke, JokeViewHolder>(JokeDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val binding = ItemJokeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JokeViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}
