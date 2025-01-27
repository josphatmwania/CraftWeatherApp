package jmwania.weather.data.home.mappers

import jmwania.weather.data.home.model.CityCoordinatesDataModel
import jmwania.weather.data.home.model.CityForecastDataModel
import jmwania.weather.data.home.model.ForecastDataModel
import jmwania.weather.data.home.utils.NumberUtils.orZero
import jmwania.weather.remotedatasource.models.ForecastApiModel
import jmwania.weather.remotedatasource.models.WeatherApiModel

object ApiToDataModelMapper {
    fun WeatherApiModel.toData(forecast: ForecastApiModel) = ForecastDataModel(
        cityName = forecast.city?.name.orEmpty(),
        cityId = forecast.city?.id ?: 0,
        cityForecast = forecast.forecast?.toData() ?: emptyList(),
        coordinates = CityCoordinatesDataModel(
            latitude = coord?.lat.orZero(),
            longitude = coord?.lon.orZero()
        ),
        seaLevel = forecast.forecast?.firstOrNull()?.main?.seaLevel ?: 0,
        weather = weather?.firstOrNull()?.main.orEmpty(),
        minimumTemperature = main?.tempMin.orZero(),
        maximumTemperature = main?.tempMax.orZero(),
        currentTemperature = main?.temp.orZero()
    )

    fun List<ForecastApiModel.DayForecastApiModel>.toData() = map {
        CityForecastDataModel(
            date = it.dtTxt.orEmpty(),
            temperature = it.main?.temp.orZero(),
            minimumTemperature = it.main?.tempMin.orZero(),
            maximumTemperature = it.main?.tempMax.orZero(),
            weather = it.weather?.firstOrNull()?.main.orEmpty()
        )
    }
}
