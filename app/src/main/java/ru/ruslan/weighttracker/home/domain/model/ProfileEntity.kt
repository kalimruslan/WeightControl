package ru.ruslan.weighttracker.home.domain.model

import kotlin.math.pow

class ProfileEntity(val fio: String?,
                    val dateBirth: String?,
                    var currentWeight: Double = 60.0,
                    var currentHeight: Double = 160.0,
                    val goalWeight: Double = 0.0,
                    val weightEntity: WeightEntity? = null,
                    val photoEntity: PhotoEntity? = null){

    var currentIMT: Double = currentWeight/ (currentHeight/100).pow(2.0)
}