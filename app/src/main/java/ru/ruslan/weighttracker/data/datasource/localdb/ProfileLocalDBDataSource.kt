package ru.ruslan.weighttracker.data.datasource.localdb

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import ru.ruslan.weighttracker.core.datatype.Result
import ru.ruslan.weighttracker.data.datasource.localdb.dao.ProfileLocalDao
import ru.ruslan.weighttracker.data.datasource.localdb.database.AppRoomDatabase
import ru.ruslan.weighttracker.data.datasource.localdb.model.PhotoLocal
import ru.ruslan.weighttracker.data.datasource.localdb.model.ProfileLocal
import ru.ruslan.weighttracker.data.datasource.localdb.model.WeightLocal
import java.lang.Exception

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
            try {
                profileLocalDao?.saveWeight(
                    profileId = weight.profileId,
                    weight = weight.weight,
                    weightDate = weight.weightDate
                )
            } catch (ex: SQLiteConstraintException) {
                ex.printStackTrace()
            }
        }
    }

    suspend fun savePhotoData(photoLocal: PhotoLocal?) {
        photoLocal?.let { photo ->
            try {
                profileLocalDao?.savePhoto(
                    profileId = photo.profileId,
                    photoUrl = photo.photoUrl,
                    photoDate = photo.photoDate
                )
            } catch (ex: SQLiteConstraintException) {
                ex.printStackTrace()
            }
        }
    }

    suspend fun insertProfile(profileLocal: ProfileLocal?): Result<Int> {
        return try {
            val profileId = profileLocal?.let { profileLocalDao?.insertProfile(it) }
            Result.success(profileId?.toInt())
        } catch (ex: Exception) {
            Result.error(ex)
        }
    }

}