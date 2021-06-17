package ru.lachesis.weather_app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.lachesis.weather_app.model.Repository
import ru.lachesis.weather_app.model.RepositoryImpl
import ru.lachesis.weather_app.model.Weather

class DayViewModel (
    val dayLiveData : MutableLiveData<List<Weather>> = MutableLiveData(),
    val repository: Repository = RepositoryImpl()
    ) : ViewModel() {
        fun getDayForecast() :List<Weather> = getDayLocalForecast()

    private fun getDayLocalForecast(): List<Weather> = repository.getDayForecast()
}