package ru.ruslan.weighttracker.profile.domain.usecase

import android.util.Log
import ru.ruslan.weighttracker.home.domain.ProfileRepository
import ru.ruslan.weighttracker.home.domain.model.PhotoEntity
import ru.ruslan.weighttracker.home.domain.model.ProfileEntity
import ru.ruslan.weighttracker.home.domain.model.WeightEntity

class SaveToProfileUseCase(private val profileRepository: ProfileRepository) {
    suspend fun saveWeight() {
        profileRepository.saveWeight(WeightEntity(1, 113.0, "01.01.2019"))
    }

    suspend fun savePhoto() {
        profileRepository.savePhotoData(PhotoEntity(1, "www", "01.01.2019"))
    }

    suspend fun insertProfile() {
        val profileEntity = ProfileEntity(
            fio = "Альберт",
            dateBirth = "05.02.1987",
            currentWeight = 110.0,
            currentHeight = 178.0,
            goalWeight = 95.0)
        val profileId = profileRepository.createProfile(profileEntity = profileEntity)
        Log.d("PROFILE_ID", profileId.data.toString())
    }
}