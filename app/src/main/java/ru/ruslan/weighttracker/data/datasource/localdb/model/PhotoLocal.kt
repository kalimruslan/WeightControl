package ru.ruslan.weighttracker.data.datasource.localdb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    foreignKeys = [ForeignKey(entity = ProfileLocal::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("profile_id"),
        onDelete = ForeignKey.CASCADE)]
)
data class PhotoLocal(@field:ColumnInfo(name = "profile_id") var profileId: Int,
                      @field:ColumnInfo(name = "photo_url") var photoUrl: String,
                      @field:ColumnInfo(name = "photo_date") var photoDate: String)
