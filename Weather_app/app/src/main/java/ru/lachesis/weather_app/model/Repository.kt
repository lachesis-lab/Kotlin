package ru.lachesis.weather_app.model

interface Repository {
    fun getLocalData(): Weather
    fun getRemoteData(): Weather
    fun getDayForecast(): List<Weather>
}