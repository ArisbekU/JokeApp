package data


import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jokeapp.R

class JokeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val categoryTextView: TextView = itemView.findViewById(R.id.jokeCategory)
    val questionTextView: TextView = itemView.findViewById(R.id.jokeQuestion)
    val answerTextView: TextView = itemView.findViewById(R.id.jokeAnswer)
}