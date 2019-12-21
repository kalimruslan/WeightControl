package ru.ruslan.weighttracker.data.datasource.api.model.response

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class YoutubeThumbnails(
    @field:Json(name = "default") val default: YoutubeThumbnailsDefault?,
    @field:Json(name = "medium") val medium: YoutubeThumbnailsMedium?
)
