package ru.lachesis.weather_app.repository

import ru.lachesis.weather_app.model.City
import ru.lachesis.weather_app.model.Weather
import ru.lachesis.weather_app.room.HistoryDaoInterface
import ru.lachesis.weather_app.room.HistoryEntity
import java.util.*

class HistoryRepositoryImpl(private val historyDataSource:HistoryDaoInterface): HistoryRepository {
    override fun getAllHistory(): List<Weather> {
        return convertEntityToWeather(historyDataSource.selectAll())
    }

    override fun saveHistory(weather: Weather) {
        historyDataSource.insert(convertWeatherToEntity(weather))
    }

    private fun convertWeatherToEntity(weather: Weather): HistoryEntity {
        return HistoryEntity(0,weather.city.city,weather.date.time,weather.temperature,weather.condition)
    }

    fun convertEntityToWeather(entityList: List<HistoryEntity>): List<Weather>{
        return entityList.map { Weather(City(it.city,0.0,0.0), Date(it.date),it.temperature,0,it.condition) }
    }

}