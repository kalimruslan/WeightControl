package ru.ruslan.weighttracker.home.domain

import ru.ruslan.weighttracker.data.datasource.localdb.model.ProfileLocal
import ru.ruslan.weighttracker.home.domain.model.PhotoEntity
import ru.ruslan.weighttracker.home.domain.model.ProfileEntity
import ru.ruslan.weighttracker.home.domain.model.WeightEntity

interface ProfileRepository {
    suspend fun saveWeight(weightEntity: WeightEntity)
    suspend fun savePhotoData(profileEntity: ProfileEntity)
    suspend fun getProfileData(userId: String): ProfileLocal
    suspend fun getAllProfileData(): List<ProfileLocal>
    fun clearProfile(userId: String)
    fun clearAllProfiles()
    suspend fun saveProfile(profileEntity: ProfileEntity)
}