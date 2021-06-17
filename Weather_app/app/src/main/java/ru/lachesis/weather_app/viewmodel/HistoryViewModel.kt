package ru.lachesis.weather_app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.lachesis.weather_app.app.App
import ru.lachesis.weather_app.app.AppState
import ru.lachesis.weather_app.repository.HistoryRepository
import ru.lachesis.weather_app.repository.HistoryRepositoryImpl

class HistoryViewModel(
    val historyLiveData: MutableLiveData<AppState> = MutableLiveData(),
    val historyRepository: HistoryRepository = HistoryRepositoryImpl(App.historyDao)
        ): ViewModel(){
    fun getAllHistory() {
        historyLiveData.value = AppState.Loading
        historyLiveData.value = AppState.Success( historyRepository.getAllHistory())
    }
}