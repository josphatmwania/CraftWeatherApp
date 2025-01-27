package jmwania.weather.localdatasource.location

import jmwania.weather.localdatasource.location.model.LocationDataSourceModel
import kotlinx.coroutines.flow.Flow

interface LocationSource {
    suspend fun fetchCurrentLocation(): Flow<LocationDataSourceModel>
}
