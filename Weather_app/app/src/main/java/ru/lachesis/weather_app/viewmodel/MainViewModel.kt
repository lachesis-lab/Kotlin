package ru.lachesis.weather_app.viewmodel

import android.media.MediaRouter
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.lachesis.weather_app.model.Weather
import ru.lachesis.weather_app.model.WeatherDTO
import ru.lachesis.weather_app.repository.*

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val localRepository : Repository = RepositoryImpl(),
    private val remoteRepository: MainRepository = MainRepositoryImpl(RemoteDataSource())
) : ViewModel() {

    private val listener: Repository.WeatherLoadListener = object: Repository.WeatherLoadListener {
        override fun onFailed(error: Throwable) {
            Log.e("LoadingError",error.message.toString())
        }

        override fun onLoaded(weatherDTO: WeatherDTO) {
            liveDataToObserve.value=AppState.Loading
            liveDataToObserve.postValue(AppState.Success(Weather(weatherDTO)))
        }
    }
    fun getLiveData() = liveDataToObserve
    fun getWeatherLocal(weather: Weather?) = getLocalData(weather)
    fun getWeatherRemote(weather: Weather?) = getRemoteData(weather)

    private fun getLocalData(weather: Weather?) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            Thread.sleep(1000)
            liveDataToObserve.postValue(AppState.Success(localRepository.getLocalData(weather)))
        }.start()


    }

    private fun getRemoteData(weather: Weather?) {

        liveDataToObserve.value = AppState.Loading
        remoteRepository.getWeatherFromAPI(weather?.city!!.lat,weather.city.lon,callback = object : Callback<WeatherDTO>{
            override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
                val responseWeather: WeatherDTO? = response.body()
                if (response.isSuccessful && responseWeather!=null)
                    liveDataToObserve.value = AppState.Success(Weather(responseWeather))
                else
                    liveDataToObserve.value = AppState.Error(Exception(SERVER_ERROR))
            }

            override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                liveDataToObserve.value = AppState.Error(Exception(REQUEST_ERROR))
            }
        })
//        liveDataToObserve.postValue(AppState.Success(remoteRepository.getWeatherFromAPI(weather.city.lat,weather.city.lon,callback))
//        liveDataToObserve.postValue(AppState.Success(repository.getRemoteData(weather,listener)))
//        liveDataToObserve.postValue((AppState.Success(bindService,))
    }

/*
    interface WeatherLoadListener {
        fun onLoaded(weatherDTO: WeatherDTO)
        fun onFailed(error: Throwable)
    }
*/

}


