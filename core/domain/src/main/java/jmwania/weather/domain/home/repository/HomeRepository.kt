package jmwania.weather.domain.home.repository

import jmwania.weather.domain.home.model.HomeRepositoryDomainModel
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun fetchHomeInformation(): Flow<HomeRepositoryDomainModel>
}
