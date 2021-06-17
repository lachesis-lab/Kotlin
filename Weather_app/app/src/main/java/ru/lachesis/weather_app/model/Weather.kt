package ru.lachesis.weather_app.model



import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import java.util.*

data class Weather(
    val city: City = getDefaultCity(),
    val date: Date = Calendar.getInstance(Locale.getDefault()).time,
//        get {return java.text.SimpleDateFormat.getDateTimeInstance(1, 1, Locale.getDefault()).format(date)},
    val temperature: Int = 0,
    val feelsLike: Int = 0
) {
    public fun getDateString(): String {
        return java.text.SimpleDateFormat.getDateTimeInstance(3, 3, Locale.getDefault()).format(date)

    }
}

