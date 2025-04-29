package com.argel.weathertraker.data.dao

//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import androidx.room.Update
//import com.hybridge.HybridgeAppAndroid.data.entity.GroupEntity
//import kotlinx.coroutines.flow.Flow

//@Dao
//interface WeatherDao {
//    @Query("SELECT * FROM groups")
//    fun getAllGroups(): Flow<List<GroupEntity>>
//
//    @Query("SELECT * FROM groups WHERE id = :id")
//    suspend fun getGroupById(id: String): GroupEntity?
//
//    @Query("DELETE FROM groups WHERE id = :id")
//    suspend fun deleteGroupById(id: String)
//
//    @Query("DELETE FROM groups")
//    suspend fun deleteAllGroups()
//
//    @Update
//    fun updateGroup(groupEntity: GroupEntity)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun saveGroup(groupEntity: GroupEntity)
//}