package ru.lachesis.weather_app.view


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.lachesis.weather_app.R

class MainActivity :  AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val fManager = supportFragmentManager.beginTransaction()
            fManager.replace(R.id.root_container, MainFragment.newInstance()).commit()



        }
    }
}