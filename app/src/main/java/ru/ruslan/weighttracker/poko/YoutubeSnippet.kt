package ru.ruslan.weighttracker.poko

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class YoutubeSnippet(
    @field:Json(name = "title") val title: String,
    @field:Json(name = "description") val description: String,
    @field:Json(name = "thumbnails") val thumbnails: YoutubeThumbnails?,
    @field:Json(name = "channelTitle") val channelTitle: String
) : Parcelable

