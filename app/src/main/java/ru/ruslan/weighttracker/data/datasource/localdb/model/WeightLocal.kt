package ru.ruslan.weighttracker.data.datasource.localdb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(entity = ProfileLocal::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("profile_id"),
        onDelete = ForeignKey.CASCADE)]
)
data class WeightLocal(@field:PrimaryKey(autoGenerate = true) var id: Int,
                       @field:ColumnInfo(name = "profile_id") val profileId: Int,
                       @field:ColumnInfo(name = "weight") val weight: Double,
                       @field:ColumnInfo(name = "weight_date") val weightDate: String)

