package ru.lachesis.weather_app.model

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
