package jmwania.weather.remotedatasource.api

import jmwania.weather.remotedatasource.models.ForecastApiModel
import jmwania.weather.remotedatasource.models.LocationRequestModel
import jmwania.weather.remotedatasource.models.WeatherApiModel
import jmwania.weather.remotedatasource.utils.HttpClientFactory
import jmwania.weather.remotedatasource.utils.mockForecastResponse
import jmwania.weather.remotedatasource.utils.mockWeatherResponse
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class WeatherRemoteSourceImplTest {

    @Test
    fun `Given success result When fetchWeather Then returns data`() {
        val mockEngine = MockEngine {
            respond(
                content = mockWeatherResponse,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val client = HttpClientFactory().create(mockEngine)

        runBlocking {
            // When
            val request = LocationRequestModel(0.0, 0.0)
            val response = WeatherRemoteSourceImpl(client, "").fetchWeather(request)

            // Then
            val expected = Json.decodeFromString<WeatherApiModel>(mockWeatherResponse)
            assertEquals(expected, response)
        }
    }

    @Test
    fun `Given success result When fetchForecast Then returns data`() {
        val mockEngine = MockEngine {
            respond(
                content = mockForecastResponse,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val client = HttpClientFactory().create(mockEngine)

        runBlocking {
            // When
            val request = LocationRequestModel(0.0, 0.0)
            val response = WeatherRemoteSourceImpl(client, "").fetchForecast(request)

            // Then
            val expected = Json.decodeFromString<ForecastApiModel>(mockForecastResponse)
            assertEquals(expected, response)
        }
    }
}
