package ru.ruslan.weighttracker.data.datasource.localdb

import ru.ruslan.weightracker.core.datatype.Result
import ru.ruslan.weighttracker.data.datasource.localdb.database.AppRoomDatabase
import ru.ruslan.weighttracker.data.datasource.localdb.model.PhotoLocal
import ru.ruslan.weighttracker.data.datasource.localdb.model.ProfileLocal
import ru.ruslan.weighttracker.data.datasource.localdb.model.WeightLocal
import java.lang.Exception

class ProfileLocalDBDataSource(private val roomDatabase: AppRoomDatabase) {

    suspend fun savePhotoData(photoLocal: PhotoLocal?) : Result<Int> {
        return try {
            val photoId = photoLocal?.let { roomDatabase.photoLocalDao().savePhoto(it) }
            Result.success(photoId?.toInt())
        } catch (ex: Exception) {
            Result.error(ex)
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

    suspend fun editProfile(profileId: Int, profileLocal: ProfileLocal?) {
        profileLocal?.let {
            roomDatabase.profileLocalDao().updateProfile(
                profileId,
                profileLocal.fio,
                profileLocal.dateBirth,
                profileLocal.currHeight,
                profileLocal.currWeight,
                profileLocal.currIMT,
                profileLocal.goalWeight
            )
        }
    }

    suspend fun getProfile(userId: Int): Result<ProfileLocal> {
        return try {
            val profileLocal = roomDatabase.profileLocalDao().getProfile(userId)
            Result.success(profileLocal)
        } catch (ex: Exception) {
            Result.error(ex)
        }
    }

    fun getLastPhotoData(onlyLast: Boolean, userId: Int): Result<PhotoLocal> {
        return try {
            val photoLocal: PhotoLocal? = if(onlyLast){
                roomDatabase.photoLocalDao().getLastPhotoLocal(userId)
            } else{
                roomDatabase.photoLocalDao().getPhotoLocal(userId)
            }

            Result.success(photoLocal)
        } catch (ex: Exception) {
            Result.error(ex)
        }
    }

    suspend fun saveWeight(weightLocal: WeightLocal?) {
        weightLocal?.let { weight ->
            try {
                roomDatabase.weightLocalDao().saveWeight(
                    profileId = weight.profileId,
                    photoId = weight.photoId,
                    weight = weight.weight,
                    weightDate = weight.weightDate
                )
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    suspend fun getWeightByPhoto(photoId: Int = 0): Result<WeightLocal>{
        return try {
            val weightLocal: WeightLocal? = roomDatabase.weightLocalDao().getWeightByPhotoId(photoId)
            Result.success(weightLocal)
        } catch (ex: Exception) {
            Result.error(ex)
        }
    }

    fun getAllWeights(userId: Int): Result<List<WeightLocal>>{
        return try {
            val weightLocal: List<WeightLocal>? = roomDatabase.weightLocalDao().getAllWeight(userId)
            Result.success(weightLocal)
        } catch (ex: Exception) {
            Result.error(ex)
        }
    }

    suspend fun getPhotoById(photoId: Int): Result<PhotoLocal> {
        return try {
            val photoLocal: PhotoLocal? = roomDatabase.photoLocalDao().getPhotoById(photoId)
            Result.success(photoLocal)
        } catch (ex: Exception) {
            Result.error(ex)
        }
    }

    fun deleteWeight(photoId: Int, photoDate: String?, weightOnPhoto: String?) {
        if(photoId > 0){
            roomDatabase.photoLocalDao().removeById(photoId)
            roomDatabase.weightLocalDao().removeByPhotoId(photoId)
        }
        else{
            roomDatabase.weightLocalDao().remove(photoDate, weightOnPhoto)
        }
    }

}