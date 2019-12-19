package ru.ruslan.weighttracker.home.domain

import ru.ruslan.weighttracker.core.datatype.Result
import ru.ruslan.weighttracker.data.datasource.localdb.model.ProfileLocal
import ru.ruslan.weighttracker.home.domain.model.PhotoEntity
import ru.ruslan.weighttracker.home.domain.model.ProfileEntity
import ru.ruslan.weighttracker.home.domain.model.WeightEntity

interface ProfileRepository {
    suspend fun saveWeight(weightEntity: WeightEntity)
    suspend fun savePhotoData(photoEntity: PhotoEntity)
    suspend fun getProfileData(userId: String): ProfileLocal
    suspend fun getAllProfileData(): List<ProfileLocal>
    fun clearProfile(userId: String)
    fun clearAllProfiles()
    suspend fun createProfile(profileEntity: ProfileEntity): Result<Long>
}