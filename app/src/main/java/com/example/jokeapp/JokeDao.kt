package com.example.jokeapp

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import data.Joke

@Dao
interface JokeDao {
    // Вставка пользовательских шуток (заменяет старую запись, если id совпадает)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserJoke(userJoke: UserJokeEntity)

    // Вставка кэшированных шуток (заменяет старую запись, если id совпадает)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCachedJokes(cachedJokes: List<CachedJokeEntity>)

    // Получить все шутки, добавленные пользователем
    @Query("SELECT * FROM user_jokes")
    suspend fun getAllUserJokes(): List<UserJokeEntity>

    // Получить все кэшированные шутки
    @Query("SELECT * FROM cached_jokes")
    suspend fun getAllCachedJokes(): List<CachedJokeEntity>

    // Очистить старые шутки из кэша, которые старше определенного времени
    @Query("DELETE FROM cached_jokes WHERE timestamp < :expiryTime")
    suspend fun clearOldJokes(expiryTime: Long)

    // Получить шутки по категории
    @Query("SELECT * FROM user_jokes WHERE category = :category")
    suspend fun getUserJokesByCategory(category: String): List<UserJokeEntity>

    @Query("DELETE FROM cached_jokes WHERE timestamp < :expiryTime")
    suspend fun clearOldCachedJokes(expiryTime: Long)
}

