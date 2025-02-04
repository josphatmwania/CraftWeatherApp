package jmwania.weather.data.home.repository

import jmwania.weather.data.home.mappers.DataToDomainModelMapper
import jmwania.weather.data.home.mappers.DatabaseToDataModelMapper
import jmwania.weather.data.home.utils.DateAndTimeUtils
import jmwania.weather.domain.home.model.ForecastDomainModel
import jmwania.weather.domain.home.model.HomeRepositoryDomainModel
import jmwania.weather.domain.home.model.WeatherDomainModel.Sunny
import jmwania.weather.localdatasource.city.dao.CityDao
import jmwania.weather.localdatasource.city.model.CityEntity
import jmwania.weather.localdatasource.location.LocationSource
import jmwania.weather.localdatasource.weather.dao.ForecastDao
import jmwania.weather.localdatasource.weather.dao.WeatherDao
import jmwania.weather.localdatasource.weather.model.ForecastEntity
import jmwania.weather.localdatasource.weather.model.WeatherLocalSourceEntity
import jmwania.weather.remotedatasource.api.WeatherRemoteSource
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
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
private val givenForecast = ForecastEntity(
    id = 1,
    date = "2023-08-24 12:00:00",
    minimumTemperature = 10.0,
    maximumTemperature = 22.0,
    temperature = 20.0,
    weather = "Clear",
    cityId = 1
)

private val expectedValue = HomeRepositoryDomainModel(
    currentTemperature = 19,
    minimumTemperature = 12,
    maximumTemperature = 20,
    weather = Sunny,
    seaLevel = 1,
    daysAheadForecast = listOf(
        ForecastDomainModel(day = "2023-08-24 12:00:00", weather = Sunny, temperature = 20)
    ),
    cityName = "Gotham"
)

class HomeDataRepositoryTest {
    private lateinit var classUnderTest: HomeDataRepository

    private val weatherRemoteSource = mockk<WeatherRemoteSource>()
    private val locationSource = mockk<LocationSource>()
    private val weatherDao = mockk<WeatherDao>()
    private val forecastDao = mockk<ForecastDao>()
    private val cityDao = mockk<CityDao>()
    private val dateAndTimeUtils = mockk<DateAndTimeUtils>()
    private val dataToDomainModelMapper = mockk<DataToDomainModelMapper>()

    @BeforeEach
    fun setup() {
        classUnderTest = HomeDataRepository(
            weatherRemoteSource = weatherRemoteSource,
            locationSource = locationSource,
            weatherDao = weatherDao,
            forecastDao = forecastDao,
            cityDao = cityDao,
            dateAndTimeUtils = dateAndTimeUtils,
            dataToDomainModelMapper = dataToDomainModelMapper
        )
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `Given cached data When fetchHomeInformation() Then Returns DomainModel()`() = runBlocking {
        // Given
        coEvery { weatherDao.getAll() }.returns(flowOf(givenWeatherEntity))
        coEvery { cityDao.getAll() }.returns(flowOf(givenCity))
        coEvery { forecastDao.getAll() }.returns(flowOf(listOf(givenForecast)))
        every { dateAndTimeUtils.todayInDate() } returns "2023-08-24"
        every { dateAndTimeUtils.convertToDate(10L) } returns "2023-08-24"
        val givenDataMode = DatabaseToDataModelMapper.toData(
            givenWeatherEntity,
            givenCity,
            listOf(
                givenForecast
            )
        )
        every { dataToDomainModelMapper.toDomain(givenDataMode) } returns expectedValue

        // When
        var actualValue: HomeRepositoryDomainModel? = null
        classUnderTest.fetchHomeInformation().collect {
            actualValue = it
        }

        // Then
        assertEquals(expectedValue, actualValue)
    }
}
