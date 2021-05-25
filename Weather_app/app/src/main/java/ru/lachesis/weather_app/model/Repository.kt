package ru.lachesis.weather_app.model

interface Repository {
    fun getLocalData(): Weather
    fun getRemoteData(): Weather
    fun getDayForecast(): List<Weather>
    fun getWeatherFromLocalStorageRus(): List<Weather>
    fun getWeatherFromLocalStorageWorld(): List<Weather>
}