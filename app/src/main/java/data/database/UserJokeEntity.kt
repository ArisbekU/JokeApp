package data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_jokes")
data class UserJokeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int ,
    val category: String,
    val question: String,
    val answer: String,
    val source: String,
)