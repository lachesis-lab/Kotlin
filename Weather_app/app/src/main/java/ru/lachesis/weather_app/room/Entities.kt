package ru.lachesis.weather_app.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val city: String,
    val date: Long,
    val temperature: Int,
    val condition: String
)

