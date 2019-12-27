package ru.ruslan.weighttracker.domain.usecase

import ru.ruslan.weighttracker.domain.repository.ProfilePrefencesRepository
import ru.ruslan.weightracker.core.datatype.ResultType
import ru.ruslan.weighttracker.domain.repository.ProfileRepository
import ru.ruslan.weighttracker.domain.model.profile.PhotoEntity
import ru.ruslan.weighttracker.domain.model.profile.ProfileEntity
import ru.ruslan.weighttracker.domain.model.profile.WeightEntity
import javax.inject.Inject

class SaveToProfileUseCase @Inject constructor(private val profileRepository: ProfileRepository,
                                               private val preferencesRepository: ProfilePrefencesRepository) {

    interface Callback {
        interface Profile {
            fun profileCreateSuccess()
            fun profileCreateError()
            fun profileEditSuccess()
        }

        interface Weight {
            fun weightSaveSuccess()
            fun weightSaveError()
        }

        interface Photo {
            fun photoSaveSuccess()
            fun photoSaveError()
        }
    }

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

    suspend fun insertProfile(profileEntity: ProfileEntity?, listener: Callback.Profile) {
        profileEntity?.let {
            profileRepository.createProfile(profileEntity = profileEntity).let { profileId ->
                if (profileId.resultType == ResultType.SUCCESS) {
                    profileId.data?.let {
                        preferencesRepository.storeProfileId(profileId = it)
                        listener.profileCreateSuccess()
                    }
                }
            }
        }
    }

    suspend fun editProfile(profileEntity: ProfileEntity?, listener: Callback.Profile) {
        profileEntity?.let {
            profileRepository.editProfile(profileId = preferencesRepository.retreiveProfileId(), profileEntity = profileEntity).let {
                listener.profileEditSuccess()
            }
        }
    }
}