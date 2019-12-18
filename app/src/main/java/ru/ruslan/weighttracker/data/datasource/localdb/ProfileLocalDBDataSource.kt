package ru.ruslan.weighttracker.data.datasource.localdb

import ru.ruslan.weighttracker.data.datasource.localdb.dao.ProfileLocalDao
import ru.ruslan.weighttracker.data.datasource.localdb.database.AppRoomDatabase
import ru.ruslan.weighttracker.data.datasource.localdb.model.ProfileLocal

class ProfileLocalDBDataSource(private val roomDatabase: AppRoomDatabase) {

    private var profileLocalDao: ProfileLocalDao? = null

    companion object {
        private var INSTANCE: ProfileLocalDBDataSource? = null

        fun getInstance(roomDatabase: AppRoomDatabase): ProfileLocalDBDataSource {
            if (INSTANCE == null)
                INSTANCE = ProfileLocalDBDataSource(roomDatabase)
            return INSTANCE as ProfileLocalDBDataSource
        }
    }

    init {
        profileLocalDao = roomDatabase.profileLocalDao()
    }

    suspend fun saveWeight(profileLocal: ProfileLocal?) {
        profileLocal?.let { profile ->
            if (profile.weightLocal == null) return
            profileLocalDao?.saveWeight(
                profileId = profile.weightLocal.profileId,
                weight = profile.weightLocal.weight,
                weightDate = profile.weightLocal.weightDate
            )
        }
    }

    suspend fun savePhotoData(profileLocal: ProfileLocal?) {
        profileLocal?.let { profile ->
            if (profile.photoLocal == null) return
            profileLocalDao?.savePhoto(
                profileId = profile.photoLocal.profileId,
                photoUrl = profile.photoLocal.photoUrl,
                photoDate = profile.photoLocal.photoDate
            )
        }
    }

}