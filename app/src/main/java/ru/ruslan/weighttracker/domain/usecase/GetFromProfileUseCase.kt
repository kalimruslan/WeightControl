package ru.ruslan.weighttracker.domain.usecase

import ru.ruslan.weightracker.core.datatype.ResultType
import ru.ruslan.weighttracker.domain.model.PhotoDataEntity
import ru.ruslan.weighttracker.domain.model.profile.ProfileEntity
import ru.ruslan.weighttracker.domain.model.profile.WeightEntity
import ru.ruslan.weighttracker.domain.repository.ProfileLocalRepository
import ru.ruslan.weighttracker.ui.util.Constants
import javax.inject.Inject

class GetFromProfileUseCase @Inject constructor (private val profileLocalRepository: ProfileLocalRepository) {

    interface Callback{
        interface GetProfile{
            fun getProfileSuccess(profileEntity: ProfileEntity?)
            fun getProfileError()
        }
        interface GetDataForPhoto{
            fun success(photoDataEntity: PhotoDataEntity)
        }
        interface GetWeightDataList{
            fun success(list: List<WeightEntity>?)
            fun error(e: String)
        }
    }

    suspend fun getCurrentProfile(listener: Callback.GetProfile) {
        val measuresMap = mutableMapOf<String, String>()
        measuresMap.put(Constants.KEY_WEIGHT_MEASURE,
            profileLocalRepository.retrieveWeightMeasure()!!)
        measuresMap.put(Constants.KEY_HEIGHT_MEASURE,
            profileLocalRepository.retrieveHeightMeasure()!!)

        val profileEntity = profileLocalRepository.getProfileData(profileLocalRepository.retrieveProfileId())
        if(profileEntity.resultType == ResultType.SUCCESS){
            profileEntity.data?.measuresMap = measuresMap
            listener.getProfileSuccess(profileEntity.data)
            return
        }

        listener.getProfileError()
    }

    suspend fun getDataForPhoto(listener: Callback.GetDataForPhoto){
        val photoDataEntity = profileLocalRepository.getPhotoData(profileLocalRepository.retrieveProfileId())
        listener.success(photoDataEntity)
    }

    suspend fun getWeightsDataList(listener: Callback.GetWeightDataList){
        val listDataEntity = profileLocalRepository.getAllWeightsForUser(profileLocalRepository.retrieveProfileId())
        if(listDataEntity.resultType == ResultType.SUCCESS){
            // TODO Получить данные фотки
            listener.success(listDataEntity.data)
        }
        else{
            listener.error(listDataEntity.error?.message!!)
        }
    }


    fun checkIfUserExist(): Boolean = profileLocalRepository.retrieveProfileId() > 0
}