package ru.lachesis.weather_app.repository

import ru.lachesis.weather_app.model.Weather
import ru.lachesis.weather_app.model.WeatherDTO

interface Repository {
    fun getLocalData(weather: Weather?): Weather
    fun getRemoteData(weather: Weather?, listener: WeatherLoadListener): Weather
    fun getDayForecast(): List<Weather>
    fun getWeatherFromLocalStorageRus(): List<Weather>
    fun getWeatherFromLocalStorageWorld(): List<Weather>

    interface WeatherLoadListener {
        fun onLoaded(weatherDTO: WeatherDTO)
        fun onFailed(error: Throwable)
    }
}
