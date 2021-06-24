package ru.lachesis.weather_app.app

import android.app.Application
import android.content.Context
import androidx.room.Room
import ru.lachesis.weather_app.room.Database
import ru.lachesis.weather_app.room.HistoryDaoInterface

class App: Application() {

    companion object {
        private var appInstance: App? = null
        private var db: Database? = null
        private val dbName = "History.db"
        val appContext: Context by lazy { appInstance!!.applicationContext}
        val historyDao: HistoryDaoInterface by lazy {
            Room.databaseBuilder(
                appInstance!!.applicationContext,
                Database::class.java,
                dbName
            )
                .allowMainThreadQueries()
                .build().historyDao()
        }


    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }
}