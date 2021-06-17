package ru.lachesis.weather_app.model

import java.util.*

class RepositoryImpl: Repository {
    override fun getLocalData(): Weather {
        return Weather()
    }

    override fun getRemoteData(): Weather {
        return Weather()
    }

    override fun getDayForecast(): List<Weather> {
            val dayWeather = ArrayList<Weather>()
            var i = 0
            val city = getDefaultCity()
            val curDate = Calendar.getInstance(Locale.getDefault())
            while (i < 24) {
                curDate.add(Calendar.HOUR, 1)
                dayWeather.add(
                    Weather(city, curDate.time as Date, 2, 1))
                i++
            }
            return dayWeather
        }

}