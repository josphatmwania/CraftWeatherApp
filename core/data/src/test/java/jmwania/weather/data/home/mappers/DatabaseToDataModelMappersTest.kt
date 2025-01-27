package jmwania.weather.data.home.mappers

import jmwania.weather.data.home.model.CityCoordinatesDataModel
import jmwania.weather.data.home.model.CityForecastDataModel
import jmwania.weather.data.home.model.ForecastDataModel
import jmwania.weather.localdatasource.city.model.CityEntity
import jmwania.weather.localdatasource.weather.model.ForecastEntity
import jmwania.weather.localdatasource.weather.model.WeatherLocalSourceEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private val givenWeatherEntity = WeatherLocalSourceEntity(
    cityId = 1,
    cityName = "Gotham",
    seaLevel = 1,
    weather = "Clear",
    minimumTemperature = 12.0,
    maximumTemperature = 20.0,
    temperature = 19.0,
    createdAt = 10L
)
private val givenCity = CityEntity(
    cityId = 1,
    cityName = "Gotham",
    isCurrent = true,
    latitude = 10.0,
    longitude = 10.0
)
private val givenForecast = listOf(
    ForecastEntity(
        id = 1,
        date = "2023-08-24 12:00:00",
        minimumTemperature = 10.0,
        maximumTemperature = 22.0,
        temperature = 20.0,
        weather = "Clear",
        cityId = 1
    )
)

private val expectedDataModel = ForecastDataModel(
    cityId = 1,
    cityName = "Gotham",
    cityForecast = listOf(
        CityForecastDataModel(
            date = "2023-08-24 12:00:00",
            temperature = 20.0,
            minimumTemperature = 10.0,
            maximumTemperature = 22.0,
            weather = "Clear"
        )
    ),
    seaLevel = 1,
    weather = "Clear",
    coordinates = CityCoordinatesDataModel(10.0, 10.0),
    minimumTemperature = 12.0,
    maximumTemperature = 20.0,
    currentTemperature = 19.0
)

class DatabaseToDataModelMappersTest {
    private val classUnderTest = DatabaseToDataModelMapper

    @Test
    fun `Given forecast, weather and city database models When toData Then maps correctly`() {
        // When
        val actual = classUnderTest.toData(givenWeatherEntity, givenCity, givenForecast)

        // Then
        assertEquals(expectedDataModel, actual)
    }
}
