package ru.ruslan.weighttracker.home.domain.model

import ru.ruslan.weighttracker.data.datasource.localdb.model.PhotoLocal
import ru.ruslan.weighttracker.data.datasource.localdb.model.WeightLocal

class ProfileEntity(val id: Int,
                    val fio: String?,
                    val dateBirth: String?,
                    val weightEntity: WeightLocal?,
                    val photoEntity: PhotoLocal?)