package data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_jokes")
data class CachedJokeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val category: String,
    val question: String,
    val answer: String,
    val timestamp: Long
)