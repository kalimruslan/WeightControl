package ru.ruslan.weighttracker.data.datasource.api.model.response

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class YoutubeResponce(
    @SerializedName("kind") val kind: String,
    @SerializedName("etag") val etag: String,
    @SerializedName("nextPageToken") val nextPageToken: String,
    @SerializedName("prevPageToken") val prevPageToken: String,
    @SerializedName("pageInfo") val pageInfo: YoutubePageInfo,
    @SerializedName("items") val items: List<YoutubeItems>
)