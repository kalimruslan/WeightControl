package ru.ruslan.weighttracker.domain.usecase

import ru.ruslan.weightracker.core.datatype.ResultType
import ru.ruslan.weighttracker.domain.model.PhotoDataEntity
import ru.ruslan.weighttracker.domain.model.profile.ProfileEntity
import ru.ruslan.weighttracker.domain.model.profile.WeightEntity
import ru.ruslan.weighttracker.domain.repository.ProfileLocalRepository
import ru.ruslan.weighttracker.ui.util.Constants
import javax.inject.Inject

class GetFromProfileUseCase @Inject constructor(
    private val profileLocalRepository: ProfileLocalRepository) {

    suspend fun getCurrentProfile(getPofileSuccess: (ProfileEntity?) -> Unit,
                                  getProfileError: () -> Unit) {
        val measuresMap = mutableMapOf<String, String>()
        measuresMap[Constants.KEY_WEIGHT_MEASURE] = profileLocalRepository.retrieveWeightMeasure()!!
        measuresMap[Constants.KEY_HEIGHT_MEASURE] = profileLocalRepository.retrieveHeightMeasure()!!

        val profileEntity =
            profileLocalRepository.getProfileData(profileLocalRepository.retrieveProfileId())
        if (profileEntity.resultType == ResultType.SUCCESS) {
            profileEntity.data?.measuresMap = measuresMap
            getPofileSuccess(profileEntity.data)
            return
        }

        getProfileError()
    }

    suspend fun getDataForPhoto(photoDataSuccess: (PhotoDataEntity) -> Unit) {
        val photoDataEntity =
            profileLocalRepository.getPhotoData(profileLocalRepository.retrieveProfileId())
        photoDataSuccess(photoDataEntity)
    }

    suspend fun getDataByPhotoId(photoId: Int, photoDataSuccess: (PhotoDataEntity) -> Unit) {
        val photoDataEntity = profileLocalRepository.getPhotoDataByPhotoId(photoId)
        photoDataSuccess(photoDataEntity)
    }

    suspend fun getWeightsDataList(weigthListSuccess: (List<WeightEntity>?) -> Unit,
                                   weightListError: (String) -> Unit) {
        val listDataEntity =
            profileLocalRepository.getAllWeightsForUser(profileLocalRepository.retrieveProfileId())
        if (listDataEntity.resultType == ResultType.SUCCESS) {
            weigthListSuccess(listDataEntity.data)
        } else {
            weightListError(listDataEntity.error?.message!!)
        }
    }


    fun checkIfUserExist(): Boolean = profileLocalRepository.retrieveProfileId() > 0
}