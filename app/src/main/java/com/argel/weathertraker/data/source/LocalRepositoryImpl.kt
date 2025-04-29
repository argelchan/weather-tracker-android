package com.argel.weathertraker.data.source

//import com.hybridge.HybridgeAppAndroid.data.dao.RecordingsDao
//import com.hybridge.HybridgeAppAndroid.data.entity.RecordingEntity
//import com.hybridge.HybridgeAppAndroid.domain.repository.LocalRepository
//import javax.inject.Inject

/**
 * Created by Argel Chan on 20/01/2025.
 * didier.chan@ingenierosmafur.com
 */

//class LocalRepositoryImpl @Inject constructor(
//    private val recordingsDao: RecordingsDao
//): LocalRepository {
//    override suspend fun getRecordingById(id: String): RecordingEntity? {
//        return recordingsDao.getRecordingById(id)
//    }
//
//    override suspend fun insertRecording(recording: RecordingEntity) {
//        recordingsDao.saveRecording(recording)
//    }
//
//    override suspend fun deleteRecordingById(id: String) {
//        recordingsDao.deleteRecordingById(id)
//    }
//
//    override suspend fun getRecordings(): List<RecordingEntity> {
//        return recordingsDao.getAllRecordings()
//    }
//
//    override suspend fun searchRecordings(query: String): List<RecordingEntity> {
//        return recordingsDao.searchRecordings(query)
//    }
//}