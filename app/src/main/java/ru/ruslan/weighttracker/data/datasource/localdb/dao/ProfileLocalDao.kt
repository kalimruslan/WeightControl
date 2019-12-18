package ru.ruslan.weighttracker.data.datasource.localdb.dao

import androidx.room.*
import ru.ruslan.weighttracker.data.datasource.localdb.model.ProfileLocal

@Dao
interface ProfileLocalDao {
    @Query("SELECT * FROM profile WHERE id = :id")
    suspend fun getProfile(id: Int): ProfileLocal

    @Query("INSERT INTO weight(profile_id, weight, weight_date) VALUES(:profileId, :weight, :weightDate) WHERE profile.id = :profileId")
    suspend fun saveWeight(profileId: Int, weight: Double, weightDate: String)

    @Query("INSERT INTO photos(profile_id, photo_url, photo_date) VALUES(:profileId, :photoUrl, :photoDate)")
    suspend fun savePhoto(profileId: Int, photoUrl: String, photoDate: String)

    @Query("SELECT * FROM profile")
    suspend fun getAllProfiles(): List<ProfileLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: ProfileLocal)

    @Update
    suspend fun updateProfile(profile: ProfileLocal)

    @Query("DELETE FROM profile")
    suspend fun deleteAllProfiles()
}