package ru.lachesis.weather_app.repository

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ru.lachesis.weather_app.model.Weather
import ru.lachesis.weather_app.view.WEATHER_BROADCAST_EXTRA
import ru.lachesis.weather_app.view.WEATHER_BROADCAST_INTENT_FILTER
import ru.lachesis.weather_app.app.AppState


class WeatherService : Service() {

    inner class ServiceBinder : Binder() {
        fun getService(): WeatherService {
            return this@WeatherService
        }
    }

    private val binder: IBinder = ServiceBinder()

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun sendIntent(weather: Weather?) {
        Thread(Runnable {
            val broadcastIntent = Intent(WEATHER_BROADCAST_INTENT_FILTER)
            val loader = weather?.city?.let { WeatherLoaderToService(it.lat, it.lon) }
            val state: AppState? = loader?.loadWeatherToService()
            when (state) {
                is AppState.Success ->
                    broadcastIntent.putExtra(WEATHER_BROADCAST_EXTRA, state.weather[0])

                is AppState.Error ->
                    broadcastIntent.putExtra(WEATHER_BROADCAST_EXTRA, weather)
            }
            LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)

        }).start()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("LACHESIS", "onStartCommand")
        intent?.let {
            sendIntent(it.getParcelableExtra<Weather>(WEATHER_BROADCAST_EXTRA))
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("LACHESIS", "onCreate")
    }


}
