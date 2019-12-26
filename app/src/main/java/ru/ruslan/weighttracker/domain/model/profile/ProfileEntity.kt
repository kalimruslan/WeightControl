package ru.ruslan.weighttracker.domain.model.profile

import kotlin.math.pow

class ProfileEntity(val fio: String,
                    val dateBirth: String,
                    var currentWeight: Double = 60.0,
                    var currentHeight: Double = 160.0,
                    var sex: String = "",
                    val goalWeight: Double = 0.0,
                    val weightEntity: WeightEntity? = null,
                    val photoEntity: PhotoEntity? = null){

    lateinit var measuresMap: Map<String, String>
    var currentIMT: Double = currentWeight/ (currentHeight/100).pow(2.0)
}