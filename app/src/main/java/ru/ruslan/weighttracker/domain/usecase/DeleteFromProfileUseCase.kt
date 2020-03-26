package ru.ruslan.weighttracker.domain.usecase

import ru.ruslan.weighttracker.domain.repository.ProfileLocalRepository
import javax.inject.Inject

class DeleteFromProfileUseCase @Inject constructor(
    private val profileLocalRepository: ProfileLocalRepository){
    fun removeWeight(photoId: Int, photoDate: String?, weightOnPhoto: String?) {
        profileLocalRepository.deleteWeight(photoId, photoDate, weightOnPhoto)
    }

}
