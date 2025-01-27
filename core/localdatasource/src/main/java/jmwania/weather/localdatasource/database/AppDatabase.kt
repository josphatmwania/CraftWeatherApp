package jmwania.weather.localdatasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import jmwania.weather.localdatasource.city.dao.CityDao
import jmwania.weather.localdatasource.city.model.CityEntity
import jmwania.weather.localdatasource.weather.dao.ForecastDao
import jmwania.weather.localdatasource.weather.dao.WeatherDao
import jmwania.weather.localdatasource.weather.model.ForecastEntity
import jmwania.weather.localdatasource.weather.model.WeatherLocalSourceEntity

@Database(
    entities = [ForecastEntity::class, CityEntity::class, WeatherLocalSourceEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun cityDao(): CityDao

    abstract fun forecastDao(): ForecastDao
}
