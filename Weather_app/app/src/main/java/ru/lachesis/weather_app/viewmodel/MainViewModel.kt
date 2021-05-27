package ru.lachesis.weather_app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.lachesis.weather_app.model.Repository
import ru.lachesis.weather_app.model.RepositoryImpl
import ru.lachesis.weather_app.model.Weather

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repository : Repository = RepositoryImpl()
) : ViewModel() {
    fun getLiveData() = liveDataToObserve
    fun getWeatherLocal(weather: Weather?) = getLocalData(weather)
    fun getWeatherRemote() = getLocalData(null)

    private fun getLocalData(weather: Weather?) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            Thread.sleep(1000)
            liveDataToObserve.postValue(AppState.Success(repository.getLocalData(weather)))
        }.start()


    }

}


