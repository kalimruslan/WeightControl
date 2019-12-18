package ru.ruslan.weighttracker.data.datasource.localdb

import ru.ruslan.weighttracker.data.datasource.localdb.dao.ProfileLocalDao
import ru.ruslan.weighttracker.data.datasource.localdb.database.AppRoomDatabase
import ru.ruslan.weighttracker.data.datasource.localdb.model.ProfileLocal
import ru.ruslan.weighttracker.data.datasource.localdb.model.WeightLocal

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

    suspend fun saveWeight(weightLocal: WeightLocal?) {
        weightLocal?.let { weight ->
            profileLocalDao?.saveWeight(
                profileId = weight.profileId,
                weight = weight.weight,
                weightDate = weight.weightDate
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

   suspend fun insertProfile(profileLocal: ProfileLocal?) {
        profileLocal?.let {
            profileLocalDao?.insertProfile(it)
        }
    }

}