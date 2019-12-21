package ru.ruslan.weighttracker.data.datasource.api.model.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class YoutubeThumbnails(
    @SerializedName("default") val default: YoutubeThumbnailsDefault?,
    @SerializedName("medium") val medium: YoutubeThumbnailsMedium?
)
