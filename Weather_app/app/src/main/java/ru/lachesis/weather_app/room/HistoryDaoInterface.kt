package ru.lachesis.weather_app.room

import androidx.room.*

@Dao
interface HistoryDaoInterface {
    @Query("SELECT * FROM HistoryEntity ORDER BY id")
    fun selectAll():List<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: HistoryEntity)

    @Delete
    fun delete(entity: HistoryEntity)

    @Update
    fun update(entity: HistoryEntity)

}