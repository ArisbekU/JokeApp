package com.example.jokeapp

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [UserJokeEntity::class, CachedJokeEntity::class],
    version = 1, // Увеличивайте при изменении схемы
    exportSchema = true // Для генерации файла схемы (опционально)
)
abstract class JokeDatabase : RoomDatabase() {

    abstract val jokeDao: JokeDao

    companion object {
        @Volatile
        private var INSTANCE: JokeDatabase? = null

        fun getInstance(context: Context): JokeDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        JokeDatabase::class.java,
                        "jokes_database"
                    )
                        .fallbackToDestructiveMigration() // Удаляет данные при изменении схемы (опционально)
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}


