package ru.ruslan.weighttracker.data.datasource.localdb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import ru.ruslan.weighttracker.data.utils.Constants

@Entity(tableName = Constants.PROFILE_TABLE_NAME)
class ProfileLocal(
    @field:PrimaryKey(autoGenerate = true) var id: Int,
    @field:ColumnInfo(name = "FIO") var fio: String?,
    @field:ColumnInfo(name = "date_birth") var dateBirth: String?,
    @Ignore val weightLocal: WeightLocal?,
    @Ignore val photoLocal: PhotoLocal?)