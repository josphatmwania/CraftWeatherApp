package jmwania.weather.app.di

import jmwania.weather.data.home.mappers.DataToDomainModelMapper
import jmwania.weather.data.home.repository.HomeDataRepository
import jmwania.weather.data.home.utils.DateAndTimeUtils
import jmwania.weather.domain.home.repository.HomeRepository
import jmwania.weather.localdatasource.city.dao.CityDao
import jmwania.weather.localdatasource.location.LocationSource
import jmwania.weather.localdatasource.weather.dao.ForecastDao
import jmwania.weather.localdatasource.weather.dao.WeatherDao
import jmwania.weather.remotedatasource.api.WeatherRemoteSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideHomeRepository(
        weatherRemoteSource: WeatherRemoteSource,
        locationSource: LocationSource,
        weatherDao: WeatherDao,
        forecastDao: ForecastDao,
        cityDao: CityDao,
        dateAndTimeUtils: DateAndTimeUtils,
        dataToDomainModelMapper: DataToDomainModelMapper
    ): HomeRepository = HomeDataRepository(
        weatherRemoteSource,
        locationSource,
        weatherDao,
        forecastDao,
        cityDao,
        dateAndTimeUtils,
        dataToDomainModelMapper
    )

    @Provides
    fun providesDateAndTimeUtils() = DateAndTimeUtils()

    @Provides
    fun provides(dateAndTimeUtils: DateAndTimeUtils) = DataToDomainModelMapper(dateAndTimeUtils)
}
