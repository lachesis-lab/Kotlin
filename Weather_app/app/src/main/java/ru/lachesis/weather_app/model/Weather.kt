package ru.lachesis.weather_app.model

data class Weather (
    val city: City = getDefaultCity(),
    val temperature: Int = 0,
    val feelsLike: Int = 0

)
    private fun getDefaultCity(): City {
        return City("Москва",0.0, 0.0)
    }
