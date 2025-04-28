package com.argel.weathertraker.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.argel.weathertraker.framework.database.WeatherTrackerDataBase

@Entity(tableName = WeatherTrackerDataBase.RECORDINGS_TABLE_NAME)
data class SubjectEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "thumbnail") val thumbnail: String,
    @ColumnInfo(name = "duration") val duration: Int,
    @ColumnInfo(name = "session_date") val sessionDate: String,
    @ColumnInfo(name = "group_id") val groupId: String,
    @ColumnInfo(name = "file_path") val filePath: String,
    @ColumnInfo(name = "author") val author: String
)