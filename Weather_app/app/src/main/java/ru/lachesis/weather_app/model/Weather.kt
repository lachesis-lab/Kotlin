package ru.lachesis.weather_app.model


import android.os.Parcelable
import java.util.*
import kotlinx.android.parcel.Parcelize

@kotlinx.parcelize.Parcelize
data class Weather(
    val city: City = getDefaultCity(),
    val date: Date = Calendar.getInstance(Locale.getDefault()).time,
    val temperature: Int = 0,
    val feelsLike: Int = 0
) : Parcelable
{
    fun getDateString(): String {
        return java.text.SimpleDateFormat.getDateTimeInstance(3, 3, Locale.getDefault())
            .format(date)
    }
}

