package ru.ruslan.weighttracker.home.domain.model

class ProfileEntity(val id: Int,
                    val fio: String?,
                    val dateBirth: String?,
                    val weightEntity: WeightLocal?,
                    val photoEntity: PhotoLocal?)