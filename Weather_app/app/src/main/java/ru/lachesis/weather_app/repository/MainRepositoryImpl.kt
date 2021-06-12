package ru.lachesis.weather_app.repository

import retrofit2.Callback
import ru.lachesis.weather_app.model.WeatherDTO

class MainRepositoryImpl(private val remoteDataSource: RemoteDataSource): MainRepository {
    override fun getWeatherFromAPI(lat: Double, lon: Double, callback: Callback<WeatherDTO>) {
        remoteDataSource.getWeatherFromRemoteDataSource(lat,lon,callback)
    }
}