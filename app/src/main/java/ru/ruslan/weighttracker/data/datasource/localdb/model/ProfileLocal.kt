package ru.ruslan.weighttracker.data.datasource.localdb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import ru.ruslan.weighttracker.data.utils.Constants

@Entity(tableName = Constants.PROFILE_TABLE_NAME)
data class ProfileLocal(
    @field:ColumnInfo(name = "FIO") var fio: String = "",
    @field:ColumnInfo(name = "date_birth") var dateBirth: String = "",
    @field:ColumnInfo(name = "current_weight") var currWeight: Double = 60.0,
    @field:ColumnInfo(name = "current_height") var currHeight: Double = 160.0,
    @field:ColumnInfo(name = "sex") var sex: String = "",
    @field:ColumnInfo(name = "current_IMT") var currIMT: Double = 0.0,
    @field:ColumnInfo(name = "goal_weight") var goalWeight: Double = 0.0,
    @Ignore val weightLocal: WeightLocal? = null,
    @Ignore val photoLocal: PhotoLocal? = null) {
    @field:PrimaryKey(autoGenerate = true) var id: Int? = null
}