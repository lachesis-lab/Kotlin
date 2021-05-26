package ru.lachesis.weather_app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class City (
    val city: String,
    val lat: Double,
    val lon: Double
): Parcelable

public fun getDefaultCity(): City {
    return City("Москва", 33.021212, 88.0898)
}
