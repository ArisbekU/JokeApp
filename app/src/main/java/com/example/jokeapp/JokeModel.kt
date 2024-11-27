package com.example.jokeapp

data class JokeApiResponse(
    val jokes: List<JokeFromApi>
)

data class JokeFromApi(
    val id: String,
    val category: String,
    val question: String,
    val answer: String,
    val source: String
)
