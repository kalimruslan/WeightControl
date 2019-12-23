package ru.ruslan.weighttracker.data.repository

import ru.ruslan.weighttracker.domain.repository.ProfilePrefencesRepository
import ru.ruslan.weighttracker.data.datasource.sharedpreferences.ProfilePreferencesDataSource
import javax.inject.Inject

class ProfilePreferncesRepositoryImpl (private val profilePreferences: ProfilePreferencesDataSource) :
    ProfilePrefencesRepository {
    override fun storeProfileId(profileId: Int) {
        profilePreferences.storeProfileId(profileId)
    }

    override fun retreiveProfileId() =
        profilePreferences.retreiveProfileId()
}