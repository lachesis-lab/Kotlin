package ru.lachesis.weather_app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.lachesis.weather_app.model.City
import ru.lachesis.weather_app.model.Repository
import ru.lachesis.weather_app.model.RepositoryImpl
import ru.lachesis.weather_app.model.Weather

class CitySelectViewModel(
    private val cityLiveData : MutableLiveData<List<Weather>> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl()
    ) : ViewModel() {

        fun getLiveData() = cityLiveData

        fun getCityList(): List<Weather> = getCity()

        private fun getCity(): List<Weather> = repository.getWeatherFromLocalStorageRus()
}