package ru.lachesis.weather_app.view


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.lachesis.weather_app.R

class MainActivity :  AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_container, MainFragment.newInstance(null))
            transaction.replace(R.id.day_fragment_container,DayFragment.newInstance())
            transaction.commit()


        }

    }

    override fun onBackPressed() {
        val fManager = supportFragmentManager
        super.onBackPressed()
    }
}