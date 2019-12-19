package ru.ruslan.weighttracker

interface ProfilePrefencesRepository {
    fun storeProfileId(profileId: Int)
    fun retreiveProfileId(): Int
}