package com.example.jokeapp.network

import androidx.core.view.ContentInfoCompat.Source
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import kotlin.random.Random

data class JokeResponse(
    val jokes: List<JokeNetwork>
)

data class JokeNetwork(
    val id: Int,
    val category: String,
    val setup: String,
    val delivery: String,
    val source: String
)

object ApiClient {
    private const val BASE_URL = "https://v2.jokeapi.dev/"

    val api: JokeApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JokeApiService::class.java)
    }
}

interface JokeApiService {
    @GET("joke/Any?blacklistFlags=nsfw,religious,political,racist,sexist,explicit&type=twopart&amount=10")
    suspend fun getJokes(): JokeResponse
}
