package ru.lachesis.weather_app.model

data class WeatherDTO(
    val fact : FactDTO?
)

data class FactDTO(
    val temp: Int?,
    val feels_like: Int?,
    val condition: String?
)