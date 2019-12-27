package ru.ruslan.weighttracker.data.datasource.localdb.dao

import androidx.room.*
import ru.ruslan.weighttracker.data.datasource.localdb.model.ProfileLocal

@Dao
interface ProfileLocalDao {
    @Query("SELECT * FROM profile WHERE id = :id")
    suspend fun getProfile(id: Int): ProfileLocal

    @Query("INSERT INTO weight(profile_id, weight, weight_date) VALUES(:profileId, :weight, :weightDate)")
    suspend fun saveWeight(profileId: Int, weight: Double, weightDate: String)

    @Query("INSERT INTO photos(profile_id, photo_url, photo_date) VALUES(:profileId, :photoUrl, :photoDate)")
    suspend fun savePhoto(profileId: Int, photoUrl: String, photoDate: String)

    @Query("SELECT * FROM profile")
    suspend fun getAllProfiles(): List<ProfileLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: ProfileLocal): Long

    @Query("UPDATE profile SET FIO = :fio, date_birth = :dateBirth, " +
            "current_weight = :currWeight, current_height = :currHeight, current_IMT = :currImt, " +
            "goal_weight = :goal WHERE id = :profileId")
    suspend fun updateProfile(profileId: Int, fio: String, dateBirth: String, currHeight: Double,
                              currWeight: Double, currImt: Double, goal: Double)

    @Query("DELETE FROM profile")
    suspend fun deleteAllProfiles()
}