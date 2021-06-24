package ru.lachesis.weather_app

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseNotificationService : FirebaseMessagingService() {

    companion object {
//        private const val PUSH_KEY_TITLE = "title"
//        private const val PUSH_KEY_MESSAGE = "message"
        private const val CHANNEL_ID = "channel_id"
        private var notification_id = 0
    }

    override fun onMessageReceived(message: RemoteMessage) {
//        super.onMessageReceived(message)
        val messageData = message.data
        if (messageData.isNotEmpty())
            handleDataMessage(messageData.toMap())
        else showNotification(message.notification!!.title!!,message.notification!!.body!!)
    }

    private fun handleDataMessage(data: Map<String, String>) {
        val str: StringBuilder
        data.forEach { entry: Map.Entry<String, String> ->
            val k = entry.key
            val v = entry.value
        if (k.isNotBlank() && v.isNotBlank())
            showNotification(k, v)
        }


    }

    private fun showNotification(title: String, message: String) {
        val notificationBuilder =
            NotificationCompat.Builder(applicationContext, CHANNEL_ID).apply {
                setSmallIcon(R.drawable.ic_kotlin_logo)
                setContentTitle(title)
                setContentText(message)
                priority = NotificationCompat.PRIORITY_DEFAULT
            }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }
        notificationManager.notify(notification_id++, notificationBuilder.build())

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val name = "Channel name"
        val descriptionText = "Channel description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        notificationManager.createNotificationChannel(channel)
    }

}