package ru.ruslan.weighttracker.domain.usecase

import ru.ruslan.weightracker.core.datatype.ResultType
import ru.ruslan.weighttracker.domain.model.profile.ProfileEntity
import ru.ruslan.weighttracker.domain.repository.ProfilePrefencesRepository
import ru.ruslan.weighttracker.domain.repository.ProfileRepository
import ru.ruslan.weighttracker.ui.util.Constants
import javax.inject.Inject

class GetFromProfileUseCase @Inject constructor (private val profileRepository: ProfileRepository,
                                                 private val preferencesRepository: ProfilePrefencesRepository) {
    suspend fun getCurrentProfile(): ProfileEntity? {
        val measuresMap = emptyMap<String, String>()
        measuresMap.toMutableMap().put(Constants.KEY_WEIGHT_MEASURE,
            preferencesRepository.retrieveHeightMeasure()!!)
        measuresMap.toMutableMap().put(Constants.KEY_HEIGHT_MEASURE,
            preferencesRepository.retrieveHeightMeasure()!!)

        val profielEntity = profileRepository.getProfileData(preferencesRepository.retreiveProfileId())
        if(profielEntity.resultType == ResultType.SUCCESS){
            profielEntity.data?.measuresMap = measuresMap
            return profielEntity.data
        }

        return null
    }


    fun checkIfUserExist(): Boolean = preferencesRepository.retreiveProfileId() > 0
}