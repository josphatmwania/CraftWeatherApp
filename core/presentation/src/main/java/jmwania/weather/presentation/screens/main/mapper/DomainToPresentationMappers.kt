package jmwania.weather.presentation.screens.main.mapper

import jmwania.weather.domain.home.model.ForecastDomainModel
import jmwania.weather.domain.home.model.WeatherDomainModel
import jmwania.weather.presentation.screens.main.mapper.DateMapper.toDayOfWeek
import jmwania.weather.presentation.screens.main.model.DayForecast
import jmwania.weather.presentation.screens.main.model.WeatherPresentationModel

object DomainToPresentationMappers {

    fun WeatherDomainModel.toPresentation() = when (this) {
        WeatherDomainModel.Cloudy -> WeatherPresentationModel.ForestCloudy
        WeatherDomainModel.Sunny, WeatherDomainModel.Default -> WeatherPresentationModel.ForestSunny
        WeatherDomainModel.Rainy -> WeatherPresentationModel.ForestRainy
    }

    fun List<ForecastDomainModel>.toPresentation() = map { forecast ->
        DayForecast(
            day = forecast.day.toDayOfWeek(),
            temperature = forecast.temperature.toTemperatureString(),
            weather = forecast.weather.toPresentation()
        )
    }

    fun Int.toTemperatureString() = "$this°C"
}
