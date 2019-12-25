package ru.ruslan.weighttracker.domain.repository

import ru.ruslan.weightracker.core.datatype.Result
import ru.ruslan.weighttracker.data.datasource.localdb.model.ProfileLocal
import ru.ruslan.weighttracker.domain.model.profile.PhotoEntity
import ru.ruslan.weighttracker.domain.model.profile.ProfileEntity
import ru.ruslan.weighttracker.domain.model.profile.WeightEntity

interface ProfileRepository {
    suspend fun saveWeight(weightEntity: WeightEntity)
    suspend fun savePhotoData(photoEntity: PhotoEntity)
    suspend fun getProfileData(userId: String): ProfileLocal
    suspend fun getAllProfileData(): List<ProfileLocal>
    fun clearProfile(userId: String)
    fun clearAllProfiles()
    suspend fun createProfile(profileEntity: ProfileEntity): Result<Int>
}