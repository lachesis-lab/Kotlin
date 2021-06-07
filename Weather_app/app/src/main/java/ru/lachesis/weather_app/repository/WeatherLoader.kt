package ru.lachesis.weather_app.repository

import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import ru.lachesis.weather_app.BuildConfig
import ru.lachesis.weather_app.model.WeatherDTO
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection
import kotlin.Exception

class WeatherLoader(
    private val listener: Repository.WeatherLoadListener,
    private val lat: Double,
    private val lon: Double
) {
    @RequiresApi(Build.VERSION_CODES.N)
    fun loadWeather() {
        try {
            val uri =
                URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")
            val handler = Handler()
            Thread(Runnable {
                lateinit var urlConnection: HttpsURLConnection
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.addRequestProperty(
                        "X-Yandex-API-Key",
                        BuildConfig.WEATHER_API_KEY
                    )
                    urlConnection.readTimeout = 10000
                    val bufferedReader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))

                    val weatherDTO: WeatherDTO =
                        Gson().fromJson(getLines(bufferedReader), WeatherDTO::class.java)
                    handler.post { listener.onLoaded(weatherDTO) }
                } catch (err: Exception) {
                    Log.e("ConnectionError", err.toString())
                    err.printStackTrace()
                    listener.onFailed(err)
                } finally {
                    urlConnection.disconnect()
                }
            }).start()

        } catch (err: Exception) {
            Log.e("UriError", err.message.toString())
            err.printStackTrace()
            listener.onFailed(err)

        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }



}