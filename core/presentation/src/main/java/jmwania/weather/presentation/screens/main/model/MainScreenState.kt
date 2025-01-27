package jmwania.weather.presentation.screens.main.model

import jmwania.weather.presentation.screens.main.model.WeatherPresentationModel.ForestSunny

data class MainScreenState(
    val currentTemperature: String = "-",
    val minimumTemperature: String = "-",
    val maximumTemperature: String = "-",
    val currentWeather: WeatherPresentationModel = ForestSunny,
    val daysForecast: List<DayForecast> = emptyList(),
    val cityName: String = "-",
    val isLoading: Boolean = true
)
