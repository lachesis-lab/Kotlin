package ru.lachesis.weather_app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.lachesis.weather_app.model.Repository
import ru.lachesis.weather_app.model.RepositoryImpl
import ru.lachesis.weather_app.model.Weather

class CitySelectViewModel(
    private val cityLiveData : MutableLiveData<List<Weather>> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl()
    ) : ViewModel() {

        fun getLiveData() = cityLiveData

        fun getCityList(isDataSetRus: Boolean): List<Weather> = getCity(isDataSetRus)

        private fun getCity(isDataSetRus: Boolean): List<Weather> {
            val data : List<Weather>
            if (isDataSetRus)
                data = repository.getWeatherFromLocalStorageRus()
            else data = repository.getWeatherFromLocalStorageWorld()
            cityLiveData.value = data
            return data
        }
}