package com.example.jokeapp

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class JokeDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_joke_detail)

        // Получаем данные из Intent
        val category = intent.getStringExtra("category")
        val question = intent.getStringExtra("question")
        val answer = intent.getStringExtra("answer")

        // Устанавливаем данные в TextView
        findViewById<TextView>(R.id.categoryDetails).text = category
        findViewById<TextView>(R.id.questionDetails).text = question
        findViewById<TextView>(R.id.answerDetails).text = answer

    }

}