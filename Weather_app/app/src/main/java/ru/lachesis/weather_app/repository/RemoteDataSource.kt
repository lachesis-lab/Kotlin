package ru.lachesis.weather_app.repository

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.lachesis.weather_app.BuildConfig
import ru.lachesis.weather_app.model.WeatherDTO

class RemoteDataSource {
    private val weatherApi = Retrofit.Builder()
        .baseUrl("https://api.weather.yandex.ru/")
        .client(createOkHttpClient())
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()

            )).build().create(WeatherAPI::class.java)

    private fun createOkHttpClient(): OkHttpClient {
        val client= OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) )
        return client.build()
    }

    fun getWeatherFromRemoteDataSource(lat: Double, lon: Double, callback: Callback<WeatherDTO>){
        weatherApi.getWeather(BuildConfig.WEATHER_API_KEY,lat,lon).enqueue(callback)

    }
}