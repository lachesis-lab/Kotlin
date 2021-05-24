package ru.lachesis.weather_app.model

data class City (
    val city: String,
    val lat: Double,
    val lon: Double
)

public fun getDefaultCity(): City {
    return City("Москва", 33.021212, 88.0898)
}
