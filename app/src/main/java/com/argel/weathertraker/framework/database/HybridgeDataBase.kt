package com.argel.weathertraker.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.argel.weathertraker.data.entity.SubjectEntity

/**
 * Created by Argel on 10/03/2022.
 */

@Database(
    entities = [
        SubjectEntity::class,
    ], version = 1, exportSchema = false
)
//@TypeConverters(Converters::class)
abstract class WeatherTrackerDataBase: RoomDatabase() {
    //abstract val recordingsDao: RecordingsDao

    companion object {
        const val DATABASE_NAME = "weather_tracker_database_v1.db"
        const val RECORDINGS_TABLE_NAME = "recordings"
    }

}