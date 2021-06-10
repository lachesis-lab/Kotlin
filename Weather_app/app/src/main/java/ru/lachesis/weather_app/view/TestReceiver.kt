package ru.lachesis.weather_app.view

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ru.lachesis.weather_app.R

class TestReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("Broadcast_message","Системное сообщение:\n ${intent.action}")
        val builder=StringBuilder()
        builder.append("Системное сообщение: ")
        builder.append(intent.action)
        Toast.makeText(context,builder.toString(),Toast.LENGTH_LONG).show()
    }
}