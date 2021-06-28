package ru.lachesis.weather_app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.lachesis.weather_app.repository.Repository
import ru.lachesis.weather_app.repository.RepositoryImpl
import ru.lachesis.weather_app.model.Weather

class DayViewModel (
    private val dayLiveData : MutableLiveData<List<Weather>> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl()
    ) : ViewModel() {
        fun getLiveData() = dayLiveData
        fun getDayForecast() :List<Weather> = getDayLocalForecast()

    private fun getDayLocalForecast(): List<Weather> = repository.getDayForecast()
}