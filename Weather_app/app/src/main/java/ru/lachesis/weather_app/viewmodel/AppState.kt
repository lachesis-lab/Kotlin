package ru.lachesis.weather_app.viewmodel

import ru.lachesis.weather_app.model.Weather

sealed class AppState {
    data class Success(val weather: Weather): AppState()
    data class Error(val error: Exception): AppState()
    object Loading : AppState()

}
