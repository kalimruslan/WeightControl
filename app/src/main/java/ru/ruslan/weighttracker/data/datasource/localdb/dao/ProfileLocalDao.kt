package ru.ruslan.weighttracker.data.datasource.localdb.dao

import androidx.room.*
import ru.ruslan.weighttracker.data.datasource.localdb.model.PhotoLocal
import ru.ruslan.weighttracker.data.datasource.localdb.model.ProfileLocal
import ru.ruslan.weighttracker.data.datasource.localdb.model.WeightLocal

@Dao
interface ProfileLocalDao {
    @Query("SELECT * FROM profile WHERE id = :id")
    suspend fun getProfile(id: Int): ProfileLocal

    @Query("INSERT INTO weight(profile_id, photo_id, weight, weight_date) VALUES(:profileId, :photoId, :weight, :weightDate)")
    suspend fun saveWeight(profileId: Int, photoId: Int, weight: Double, weightDate: String)

    /*@Query("INSERT INTO photos(profile_id, photo_url, photo_date) VALUES(:profileId, :photoUrl, :photoDate)")
    suspend fun savePhoto(profileId: Int, photoUrl: String, photoDate: String): Long*/

    @Query("SELECT * FROM profile")
    suspend fun getAllProfiles(): List<ProfileLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePhoto(photoLocal: PhotoLocal): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: ProfileLocal): Long

    @Query("UPDATE profile SET FIO = :fio, date_birth = :dateBirth, " +
            "current_weight = :currWeight, current_height = :currHeight, current_IMT = :currImt, " +
            "goal_weight = :goal WHERE id = :profileId")
    suspend fun updateProfile(profileId: Int, fio: String, dateBirth: String, currHeight: Double,
                              currWeight: Double, currImt: Double, goal: Double)

    @Query("DELETE FROM profile")
    suspend fun deleteAllProfiles()


    // TODO это все надо перенести по своим DAO
    @Query("SELECT * FROM photos WHERE profile_id = :userId")
    fun getPhotoLocal(userId: Int): PhotoLocal?

    @Query("SELECT * FROM photos WHERE profile_id = :userId ORDER BY id DESC LIMIT 1")
    fun getLastPhotoLocal(userId: Int): PhotoLocal?

    @Query("SELECT * FROM weight WHERE profile_id = :userId AND photo_id = :photoId")
    fun getWeightByPhotoId(userId: Int, photoId: Int): WeightLocal?
}