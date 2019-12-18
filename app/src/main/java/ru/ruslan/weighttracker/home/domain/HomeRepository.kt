package ru.ruslan.weighttracker.home.domain

import ru.ruslan.weighttracker.data.datasource.localdb.model.ProfileLocal

interface HomeRepository {
    fun saveWeight(userId: String)
    fun savePhotoData(userId: String)
    suspend fun getProfileData(userId: String): ProfileLocal
    suspend fun getAllProfileData(): List<ProfileLocal>
    fun clearProfile(userId: String)
    fun clearAllProfiles()
}