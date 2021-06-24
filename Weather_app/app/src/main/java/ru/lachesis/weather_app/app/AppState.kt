package ru.lachesis.weather_app.app

import ru.lachesis.weather_app.model.Weather

sealed class AppState {
    data class Success(val weather: List<Weather>): AppState()
    data class Error(val error: Exception): AppState()
    object Loading : AppState()

}
