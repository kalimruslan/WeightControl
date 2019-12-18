package ru.ruslan.weighttracker.data.datasource.localdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.ruslan.weighttracker.data.datasource.localdb.model.ProfileLocal

@Dao
interface ProfileLocalDao {
    @Query("SELECT * FROM profile WHERE id = :id")
    suspend fun getProfile(id: Int): ProfileLocal

    @Query("INSERT INTO weight VALUES(:profileId, :weight, :weightDate)")
    suspend fun saveWeight(profileId: Int, weight: Double, weightDate: String)

    @Query("INSERT INTO photos VALUES(:profileId, :photoUrl, :photoDate)")
    suspend fun savePhoto(profileId: Int, photoUrl: String, photoDate: String)

    @Query("SELECT * FROM profile")
    suspend fun getAllProfiles(): List<ProfileLocal>

    @Insert
    suspend fun insertProfile(profile: ProfileLocal)

    @Update
    suspend fun updateProfile(profile: ProfileLocal)

    @Query("DELETE FROM profile")
    suspend fun deleteAllProfiles()
}