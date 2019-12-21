package ru.ruslan.weighttracker.domain.repository

interface ProfilePrefencesRepository {
    fun storeProfileId(profileId: Int)
    fun retreiveProfileId(): Int
}