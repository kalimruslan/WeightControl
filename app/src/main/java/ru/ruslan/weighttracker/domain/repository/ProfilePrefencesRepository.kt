package ru.ruslan.weighttracker.domain.repository

interface ProfilePrefencesRepository {
    fun storeProfileId(profileId: Int)
    fun retreiveProfileId(): Int
    fun storeWeightMeasure(unit: String)
    fun retrieveWeightMeasure(): String?
    fun storeHeightMeasure(unit: String)
    fun retrieveHeightMeasure(): String?
}