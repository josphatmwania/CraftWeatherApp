package jmwania.weather.presentation.screens.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jmwania.weather.domain.home.model.HomeRepositoryDomainModel
import jmwania.weather.domain.home.repository.HomeRepository
import jmwania.weather.presentation.screens.main.mapper.DomainToPresentationMappers.toPresentation
import jmwania.weather.presentation.screens.main.mapper.DomainToPresentationMappers.toTemperatureString
import jmwania.weather.presentation.screens.main.model.MainScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {
    var mainScreenState by mutableStateOf(MainScreenState())

    fun onFetchMainScreenDetails() {
        viewModelScope.launch {
            homeRepository.fetchHomeInformation().collect { homeInformation ->
                updateState(homeInformation)
            }
        }
    }

    private fun updateState(homeInformation: HomeRepositoryDomainModel) {
        val newState = mainScreenState.copy(
            currentTemperature = homeInformation.currentTemperature.toTemperatureString(),
            minimumTemperature = homeInformation.minimumTemperature.toTemperatureString(),
            maximumTemperature = homeInformation.maximumTemperature.toTemperatureString(),
            currentWeather = homeInformation.weather.toPresentation(),
            daysForecast = homeInformation.daysAheadForecast.toPresentation(),
            cityName = homeInformation.cityName,
            isLoading = false
        )

        mainScreenState = newState
    }
}
