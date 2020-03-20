package ru.ruslan.weighttracker.domain.usecase

import ru.ruslan.weightracker.core.datatype.ResultType
import ru.ruslan.weighttracker.domain.repository.ProfileLocalRepository
import ru.ruslan.weighttracker.domain.model.profile.PhotoEntity
import ru.ruslan.weighttracker.domain.model.profile.ProfileEntity
import ru.ruslan.weighttracker.domain.model.profile.WeightEntity
import javax.inject.Inject

class SaveToProfileUseCase @Inject constructor(
    private val profileLocalRepository: ProfileLocalRepository) {

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

    suspend fun saveWeight(photoId: Int = 0, weight: Double, date: String) {
        val weightEntity = WeightEntity(profileLocalRepository.retrieveProfileId(), photoId, weight, date)
        profileLocalRepository.saveWeight(weightEntity)
    }

    suspend fun savePhotoData(dateString: String, filePath: String, weightStr: String, listener: Callback.Photo) {
        profileLocalRepository.savePhotoData(
            PhotoEntity(profileLocalRepository.retrieveProfileId(), filePath, dateString)
        ).let { photoId ->
            if (photoId.resultType == ResultType.SUCCESS) {
                photoId.data?.let {
                    saveWeight(photoId = it, weight = weightStr.toDouble(), date = dateString)
                    listener.photoSaveSuccess()
                }
            }
        }
    }

    suspend fun insertProfile(profileEntity: ProfileEntity?, listener: Callback.Profile) {
        profileEntity?.let {
            profileLocalRepository.createProfile(profileEntity = profileEntity).let { profileId ->
                if (profileId.resultType == ResultType.SUCCESS) {
                    profileId.data?.let {
                        profileLocalRepository.storeProfileId(profileId = it)
                        listener.profileCreateSuccess()
                    }
                }
            }
        }
    }

    suspend fun editProfile(profileEntity: ProfileEntity?, listener: Callback.Profile) {
        profileEntity?.let {
            profileLocalRepository.editProfile(
                profileId = profileLocalRepository.retrieveProfileId(),
                profileEntity = profileEntity
            ).let {
                listener.profileEditSuccess()
            }
        }
    }
}