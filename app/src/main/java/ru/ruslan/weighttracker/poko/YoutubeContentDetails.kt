package ru.ruslan.weighttracker.poko

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class YoutubeContentDetails(
    @field:Json(name = "videoId") val videoId: String,
    @field:Json(name = "videoPublishedAt") val publishedAt: String
) : Parcelable
