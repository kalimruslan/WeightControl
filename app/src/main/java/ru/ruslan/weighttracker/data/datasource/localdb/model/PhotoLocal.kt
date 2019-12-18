package ru.ruslan.weighttracker.data.datasource.localdb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.ruslan.weighttracker.data.utils.Constants

@Entity(
    tableName = Constants.PHOTOS_TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = ProfileLocal::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("profile_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class PhotoLocal(
    @field:PrimaryKey(autoGenerate = true) var id: Int = 0,
    @field:ColumnInfo(name = "profile_id") var profileId: Int = 0,
    @field:ColumnInfo(name = "photo_url") var photoUrl: String = "",
    @field:ColumnInfo(name = "photo_date") var photoDate: String = "")
