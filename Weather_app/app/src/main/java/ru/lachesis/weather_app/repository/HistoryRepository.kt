package ru.lachesis.weather_app.repository

import ru.lachesis.weather_app.model.Weather

interface HistoryRepository {
    fun getAllHistory(): List<Weather>
    fun saveHistory(weather: Weather)
}