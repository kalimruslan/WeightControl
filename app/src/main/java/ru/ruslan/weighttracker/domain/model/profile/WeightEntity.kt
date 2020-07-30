package ru.ruslan.weighttracker.domain.model.profile


data class WeightEntity(
    val profileId: Int,
    var photoId: Int = 0,
    val weight: Double,
    val weightDate: String)
