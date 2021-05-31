package ru.lachesis.weather_app.model


import android.os.Parcelable
import java.util.*
import kotlinx.android.parcel.Parcelize

@kotlinx.parcelize.Parcelize
data class Weather(
    val city: City = getDefaultCity(),
    val date: Date = Calendar.getInstance(Locale.getDefault()).time,
    var temperature: Int = 0,
    var feelsLike: Int = 0,
    var condition: String?=""
) : Parcelable
{
    constructor (weatherDTO: WeatherDTO) : this() {
        this.temperature = weatherDTO.fact?.temp?:0
        this.feelsLike = weatherDTO.fact?.feels_like?:0
        this.condition = weatherDTO.fact?.condition?:""

    }
    fun getDateString(): String {
        return java.text.SimpleDateFormat.getDateTimeInstance(3, 3, Locale.getDefault())
            .format(date)
    }
}

