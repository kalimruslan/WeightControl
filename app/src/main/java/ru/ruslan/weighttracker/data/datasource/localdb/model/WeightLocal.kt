package ru.ruslan.weighttracker.data.datasource.localdb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.ruslan.weighttracker.data.utils.Constants

@Entity(
    tableName = Constants.WEIGHT_TABLE_NAME,
    foreignKeys = [ForeignKey(entity = ProfileLocal::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("profile_id"),
        onDelete = ForeignKey.CASCADE)]
)
data class WeightLocal(@field:PrimaryKey(autoGenerate = true) var id: Int = 0,
                       @field:ColumnInfo(name = "profile_id") val profileId: Int = 0,
                       @field:ColumnInfo(name = "weight") val weight: Double = 0.0,
                       @field:ColumnInfo(name = "weight_date") val weightDate: String = "")

