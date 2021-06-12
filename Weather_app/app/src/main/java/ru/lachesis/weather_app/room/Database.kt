package ru.lachesis.weather_app.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(HistoryEntity::class),version = 1,exportSchema = false)
abstract class Database: RoomDatabase() {
    abstract fun historyDao(): HistoryDaoInterface
}