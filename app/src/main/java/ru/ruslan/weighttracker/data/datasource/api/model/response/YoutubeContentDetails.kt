package ru.ruslan.weighttracker.data.datasource.api.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

data class YoutubeContentDetails(
    @SerializedName("videoId") val videoId: String,
    @SerializedName("videoPublishedAt") val videoPublishedAt: String
)
