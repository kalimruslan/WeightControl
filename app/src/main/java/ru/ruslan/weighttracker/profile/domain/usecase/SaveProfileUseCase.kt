package ru.ruslan.weighttracker.profile.domain.usecase

import ru.ruslan.weighttracker.home.domain.ProfileRepository
import ru.ruslan.weighttracker.home.domain.model.PhotoEntity
import ru.ruslan.weighttracker.home.domain.model.ProfileEntity
import ru.ruslan.weighttracker.home.domain.model.WeightEntity

class SaveProfileUseCase(private val profileRepository: ProfileRepository) {
    suspend fun saveWeight(){
        profileRepository.saveWeight(WeightEntity(1, 112.0, "01.01.2019"))
    }

    suspend fun insertProfile() {
        profileRepository.saveProfile(ProfileEntity(1, "Калимуллин Руслан Ильмирович", "05.02.1987",
            WeightEntity(1, 112.0, "01.01.2019"), PhotoEntity(1, "www.ru", "01.01.2019")
        ))
    }
}