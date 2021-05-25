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

    override fun getWeatherFromLocalStorageRus(): List<Weather> {
        return getRussianCities()
    }

    override fun getWeatherFromLocalStorageWorld(): List<Weather> {
        return getWorldCities()
    }

    fun getWorldCities(): List<Weather> {
        val curDate = Calendar.getInstance(Locale.getDefault())
        return listOf(
            Weather(City("Лондон", 51.5085300, -0.1257400), curDate.time, 1, 2),
            Weather(City("Токио", 35.6895000, 139.6917100), curDate.time,3, 4),
            Weather(City("Париж", 48.8534100, 2.3488000),curDate.time, 5, 6),
            Weather(City("Берлин", 52.52000659999999, 13.404953999999975), curDate.time,7, 8),
            Weather(City("Рим", 41.9027835, 12.496365500000024), curDate.time,9, 10),
            Weather(City("Минск", 53.90453979999999, 27.561524400000053), curDate.time,11, 12),
            Weather(City("Стамбул", 41.0082376, 28.97835889999999), curDate.time,13, 14),
            Weather(City("Вашингтон", 38.9071923, -77.03687070000001), curDate.time,15, 16),
            Weather(City("Киев", 50.4501, 30.523400000000038), curDate.time,17, 18),
            Weather(City("Пекин", 39.90419989999999, 116.40739630000007), curDate.time,19, 20)
        )
    }

    fun getRussianCities(): List<Weather> {
        val curDate = Calendar.getInstance(Locale.getDefault())
        return listOf(
            Weather(City("Москва", 55.755826, 37.617299900000035), curDate.time,1, 2),
            Weather(City("Санкт-Петербург", 59.9342802, 30.335098600000038), curDate.time,3, 3),
            Weather(City("Новосибирск", 55.00835259999999, 82.93573270000002), curDate.time,5, 6),
            Weather(City("Екатеринбург", 56.83892609999999, 60.60570250000001), curDate.time,7, 8),
            Weather(City("Нижний Новгород", 56.2965039, 43.936059), curDate.time,9, 10),
            Weather(City("Казань", 55.8304307, 49.06608060000008), curDate.time,11, 12),
            Weather(City("Челябинск", 55.1644419, 61.4368432), curDate.time,13, 14),
            Weather(City("Омск", 54.9884804, 73.32423610000001), curDate.time,15, 16),
            Weather(City("Ростов-на-Дону", 47.2357137, 39.701505),curDate.time, 17, 18),
            Weather(City("Уфа", 54.7387621, 55.972055400000045), curDate.time,19, 20)
        )
    }
}