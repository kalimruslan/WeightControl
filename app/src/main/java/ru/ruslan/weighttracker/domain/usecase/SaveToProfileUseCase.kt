package ru.ruslan.weighttracker.domain.usecase

import android.util.Log
import ru.ruslan.weighttracker.domain.repository.ProfilePrefencesRepository
import ru.ruslan.weighttracker.core.datatype.ResultType
import ru.ruslan.weighttracker.domain.repository.ProfileRepository
import ru.ruslan.weighttracker.domain.model.profile.PhotoEntity
import ru.ruslan.weighttracker.domain.model.profile.ProfileEntity
import ru.ruslan.weighttracker.domain.model.profile.WeightEntity

class SaveToProfileUseCase(private val profileRepository: ProfileRepository,
                           private val preferencesRepository: ProfilePrefencesRepository) {
    suspend fun saveWeight() {
        profileRepository.saveWeight(
            WeightEntity(
                preferencesRepository.retreiveProfileId(),
                113.0,
                "01.01.2019"
            )
        )
    }

    suspend fun savePhoto() {
        profileRepository.savePhotoData(
            PhotoEntity(
                preferencesRepository.retreiveProfileId(),
                "www",
                "01.01.2019"
            )
        )
    }

    suspend fun insertProfile() {
        val profileEntity =
            ProfileEntity(
                fio = "Руслан",
                dateBirth = "05.02.1987",
                currentWeight = 110.0,
                currentHeight = 178.0,
                goalWeight = 95.0
            )
        val profileId = profileRepository.createProfile(profileEntity = profileEntity)
        if(profileId.resultType == ResultType.SUCCESS){
            profileId.data?.let {
                preferencesRepository.storeProfileId(profileId = it)
            }
        }
        Log.d("PROFILE_ID", profileId.data.toString())
    }
}