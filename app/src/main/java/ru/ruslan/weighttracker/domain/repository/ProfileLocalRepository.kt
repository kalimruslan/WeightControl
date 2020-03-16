package ru.ruslan.weighttracker.domain.repository

import ru.ruslan.weightracker.core.datatype.Result
import ru.ruslan.weighttracker.data.datasource.localdb.model.ProfileLocal
import ru.ruslan.weighttracker.domain.model.PhotoDataEntity
import ru.ruslan.weighttracker.domain.model.profile.PhotoEntity
import ru.ruslan.weighttracker.domain.model.profile.ProfileEntity
import ru.ruslan.weighttracker.domain.model.profile.WeightEntity
import java.lang.invoke.CallSite

interface ProfileLocalRepository {
    suspend fun saveWeight(weightEntity: WeightEntity)
    suspend fun savePhotoData(photoEntity: PhotoEntity): Result<Int>
    suspend fun getProfileData(userId: Int): Result<ProfileEntity>
    suspend fun getAllProfileData(): Result<List<ProfileEntity>>
    fun clearProfile(userId: String)
    fun clearAllProfiles()
    suspend fun createProfile(profileEntity: ProfileEntity): Result<Int>
    suspend fun editProfile(profileId:Int,  profileEntity: ProfileEntity)
    suspend fun getPhotoData(retrieveProfileId: Int): PhotoDataEntity
    suspend fun getAllWeightsForUser(profileId: Int): Result<List<WeightEntity>>

    fun storeProfileId(profileId: Int)
    fun retrieveProfileId(): Int
    fun storeWeightMeasure(unit: String)
    fun retrieveWeightMeasure(): String?
    fun storeHeightMeasure(unit: String)
    fun retrieveHeightMeasure(): String?
}