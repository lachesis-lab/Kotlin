package ru.lachesis.weather_app.repository

import retrofit2.Callback
import ru.lachesis.weather_app.model.WeatherDTO

interface MainRepository {
    fun getWeatherFromAPI(lat:Double,lon:Double,callback: Callback<WeatherDTO>)
}