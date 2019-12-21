package ru.ruslan.weighttracker.data.datasource.api.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

data class YoutubeSnippet(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("thumbnails") val thumbnails: YoutubeThumbnails?,
    @SerializedName("channelTitle") val channelTitle: String
)

