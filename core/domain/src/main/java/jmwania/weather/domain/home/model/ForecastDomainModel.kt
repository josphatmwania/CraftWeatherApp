package jmwania.weather.domain.home.model

data class ForecastDomainModel(
    val day: String,
    val weather: WeatherDomainModel,
    val temperature: Int
)
