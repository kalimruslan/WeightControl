package ru.ruslan.weighttracker.domain.usecase

import ru.ruslan.weightracker.core.datatype.ResultType
import ru.ruslan.weighttracker.domain.repository.ProfileLocalRepository
import ru.ruslan.weighttracker.domain.model.profile.PhotoEntity
import ru.ruslan.weighttracker.domain.model.profile.ProfileEntity
import ru.ruslan.weighttracker.domain.model.profile.WeightEntity
import javax.inject.Inject

class SaveToProfileUseCase @Inject constructor(
    private val profileLocalRepository: ProfileLocalRepository) {

    suspend fun saveWeight(photoId: Int = 0, weight: Double, date: String) {
        val weightEntity = WeightEntity(profileLocalRepository.retrieveProfileId(), photoId, weight, date)
        profileLocalRepository.saveWeight(weightEntity)
    }

    suspend fun savePhotoData(dateString: String, filePath: String, weightStr: String, photoSaveSuccess: () -> Unit) {
        profileLocalRepository.savePhotoData(
            PhotoEntity(profileLocalRepository.retrieveProfileId(), filePath, dateString)
        ).let { photoId ->
            if (photoId.resultType == ResultType.SUCCESS) {
                photoId.data?.let {
                    saveWeight(photoId = it, weight = weightStr.toDouble(), date = dateString)
                    photoSaveSuccess()
                }
            }
        }
    }

    suspend fun insertProfile(profileEntity: ProfileEntity?, createSuccess: () -> Unit, createError: () -> Unit) {
        profileEntity?.let {
            profileLocalRepository.createProfile(profileEntity = profileEntity).let { profileId ->
                if (profileId.resultType == ResultType.SUCCESS) {
                    profileId.data?.let {
                        profileLocalRepository.storeProfileId(profileId = it)
                        createSuccess()
                    }
                }
                else createError()

            }
        }
    }

    suspend fun editProfile(profileEntity: ProfileEntity?, editSuccess: () -> Unit) {
        profileEntity?.let {
            profileLocalRepository.editProfile(
                profileId = profileLocalRepository.retrieveProfileId(),
                profileEntity = profileEntity
            ).let {
                editSuccess()
            }
        }
    }

}