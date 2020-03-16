package ru.ruslan.weighttracker.data.datasource.localdb.dao

import androidx.room.Dao
import androidx.room.Query
import ru.ruslan.weighttracker.data.datasource.localdb.model.WeightLocal

@Dao
interface WeightLocalDao {

    @Query("INSERT INTO weight (profile_id, photo_id, weight, weight_date) VALUES(:profileId, :photoId, :weight, :weightDate)")
    suspend fun saveWeight(profileId: Int, photoId: Int, weight: Double, weightDate: String)

    @Query("SELECT * FROM weight WHERE profile_id = :userId AND photo_id = :photoId")
    fun getWeightByPhotoId(userId: Int, photoId: Int): WeightLocal?

    @Query("SELECT * FROM weight WHERE profile_id = :userId")
    fun getAllWeight(userId: Int): List<WeightLocal>?

}