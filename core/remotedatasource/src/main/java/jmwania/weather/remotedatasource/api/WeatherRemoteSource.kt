package jmwania.weather.remotedatasource.api

import jmwania.weather.remotedatasource.models.ForecastApiModel
import jmwania.weather.remotedatasource.models.LocationRequestModel
import jmwania.weather.remotedatasource.models.WeatherApiModel

interface WeatherRemoteSource {
    suspend fun fetchWeather(request: LocationRequestModel): WeatherApiModel
    suspend fun fetchForecast(request: LocationRequestModel): ForecastApiModel
}
