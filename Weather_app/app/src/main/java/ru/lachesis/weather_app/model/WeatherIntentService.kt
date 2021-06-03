package ru.lachesis.weather_app.model

import android.app.IntentService
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ru.lachesis.weather_app.view.WEATHER_BROADCAST_EXTRA
import ru.lachesis.weather_app.view.WEATHER_BROADCAST_INTENT_FILTER

/*

// TODO: Rename actions, choose action names that describe tasks that this
// IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
private const val ACTION_FOO = "ru.lachesis.weather_app.model.action.FOO"
private const val ACTION_BAZ = "ru.lachesis.weather_app.model.action.BAZ"

// TODO: Rename parameters
private const val EXTRA_PARAM1 = "ru.lachesis.weather_app.model.extra.PARAM1"
private const val EXTRA_PARAM2 = "ru.lachesis.weather_app.model.extra.PARAM2"
*/

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.

 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.

 */
class WeatherIntentService : IntentService("WeatherIntentService") {

    inner open class ServiceBinder : Binder() {
        fun getService(): WeatherIntentService? {
            return this@WeatherIntentService
        }
    }

    private val binder: IBinder = ServiceBinder()

    override fun onBind(intent: Intent?): IBinder? {
        return binder//super.onBind(intent)
    }


    private fun sendIntent(weather: Weather?) {
        val broadcastIntent = Intent(WEATHER_BROADCAST_INTENT_FILTER)

        broadcastIntent.putExtra(WEATHER_BROADCAST_EXTRA, weather)

        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("LACHESIS", "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("LACHESIS", "onCreate")
    }

    override fun onHandleIntent(intent: Intent?) {
        intent?.let {
            sendIntent(it.getParcelableExtra<Weather>(WEATHER_BROADCAST_EXTRA))
        }


        /*
            when (intent?.action) {
                ACTION_FOO -> {
                    val param1 = intent.getStringExtra(EXTRA_PARAM1)
                    val param2 = intent.getStringExtra(EXTRA_PARAM2)
                    handleActionFoo(param1, param2)
                }
                ACTION_BAZ -> {
                    val param1 = intent.getStringExtra(EXTRA_PARAM1)
                    val param2 = intent.getStringExtra(EXTRA_PARAM2)
                    handleActionBaz(param1, param2)
                }
            }
    */
    }
/*

    */
    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     *//*

    private fun handleActionFoo(param1: String, param2: String) {
        TODO("Handle action Foo")
    }

    */
    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     *//*

    private fun handleActionBaz(param1: String, param2: String) {
        TODO("Handle action Baz")
    }
*/

    companion object {
        /**
         * Starts this service to perform action Foo with the given parameters. If
         * the service is already performing a task this action will be queued.
         *
         * @see IntentService
         */
        // TODO: Customize helper method
/*
        @JvmStatic
        fun startActionFoo(context: Context, param1: String, param2: String) {
            val intent = Intent(context, WeatherIntentService::class.java).apply {
                action = ACTION_FOO
                putExtra(EXTRA_PARAM1, param1)
                putExtra(EXTRA_PARAM2, param2)
            }
            context.startService(intent)
        }

        */
        /**
         * Starts this service to perform action Baz with the given parameters. If
         * the service is already performing a task this action will be queued.
         *
         * @see IntentService
         *//*

        // TODO: Customize helper method
        @JvmStatic
        fun startActionBaz(context: Context, param1: String, param2: String) {
            val intent = Intent(context, WeatherIntentService::class.java).apply {
                action = ACTION_BAZ
                putExtra(EXTRA_PARAM1, param1)
                putExtra(EXTRA_PARAM2, param2)
            }
            context.startService(intent)
        }
*/
    }
}