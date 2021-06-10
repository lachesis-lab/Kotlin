package ru.lachesis.weather_app.viewmodel

import android.app.Activity
import android.app.Application
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.lachesis.weather_app.model.Repository
import ru.lachesis.weather_app.model.RepositoryImpl
import ru.lachesis.weather_app.model.Weather
import ru.lachesis.weather_app.model.WeatherDTO


class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repository : Repository = RepositoryImpl()
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
            liveDataToObserve.postValue(AppState.Success(repository.getLocalData(weather)))
        }.start()


    }

    private fun getRemoteData(weather: Weather?) {

        liveDataToObserve.value = AppState.Loading

//        liveDataToObserve.postValue(AppState.Success(repository.getRemoteData(weather,listener)))
//        liveDataToObserve.postValue((AppState.Success(bindService,))
    }
/*
    public fun bindService(weather: Weather){
        val intent = Intent(context, WeatherIntentService::class.java)
        intent.putExtra(WEATHER_BROADCAST_EXTRA,weather)
        val service= WeatherIntentService.ServiceBinder()
        val conn = object: ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                val weatherService = service as WeatherIntentService.ServiceBinder
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                //weatherService = null
            }

        }

        val binder = weatherService.getService().bindService(intent,conn, Context.BIND_AUTO_CREATE)

//        service.unbindService(binder.)

    }
*/

/*
    interface WeatherLoadListener {
        fun onLoaded(weatherDTO: WeatherDTO)
        fun onFailed(error: Throwable)
    }
*/

}


