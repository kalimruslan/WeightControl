package ru.ruslan.weighttracker.home.domain.model


data class WeightEntity(
    val profileId: Int,
    val weight: Double,
    val weightDate: String)
