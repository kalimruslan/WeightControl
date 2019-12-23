package ru.ruslan.weighttracker.data.datasource.localdb

import android.database.sqlite.SQLiteConstraintException
import ru.ruslan.weightracker.core.datatype.Result
import ru.ruslan.weighttracker.data.datasource.localdb.database.AppRoomDatabase
import ru.ruslan.weighttracker.data.datasource.localdb.model.PhotoLocal
import ru.ruslan.weighttracker.data.datasource.localdb.model.ProfileLocal
import ru.ruslan.weighttracker.data.datasource.localdb.model.WeightLocal
import java.lang.Exception
import javax.inject.Inject

class ProfileLocalDBDataSource (private val roomDatabase: AppRoomDatabase) {

    suspend fun saveWeight(weightLocal: WeightLocal?) {
        weightLocal?.let { weight ->
            try {
                roomDatabase.profileLocalDao().saveWeight(
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
                roomDatabase.profileLocalDao().savePhoto(
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
            val profileId = profileLocal?.let { roomDatabase.profileLocalDao().insertProfile(it) }
            Result.success(profileId?.toInt())
        } catch (ex: Exception) {
            Result.error(ex)
        }
    }

}