package jmwania.weather.data.home.mappers

import jmwania.weather.data.home.model.CityCoordinatesDataModel
import jmwania.weather.data.home.model.CityForecastDataModel
import jmwania.weather.data.home.model.ForecastDataModel
import jmwania.weather.localdatasource.city.model.CityEntity
import jmwania.weather.localdatasource.weather.model.ForecastEntity
import jmwania.weather.localdatasource.weather.model.WeatherLocalSourceEntity

object DatabaseToDataModelMapper {

    fun toData(
        weather: WeatherLocalSourceEntity,
        city: CityEntity,
        forecast: List<ForecastEntity>
    ) = ForecastDataModel(
        cityName = city.cityName,
        cityId = city.cityId,
        cityForecast = forecast.toData(),
        coordinates = CityCoordinatesDataModel(
            latitude = city.latitude,
            longitude = city.longitude
        ),
        seaLevel = weather.seaLevel,
        weather = weather.weather,
        minimumTemperature = weather.minimumTemperature,
        maximumTemperature = weather.maximumTemperature,
        currentTemperature = weather.temperature
    )

    fun List<ForecastEntity>.toData() = map {
        CityForecastDataModel(
            date = it.date,
            temperature = it.temperature,
            minimumTemperature = it.minimumTemperature,
            maximumTemperature = it.maximumTemperature,
            weather = it.weather
        )
    }
}
