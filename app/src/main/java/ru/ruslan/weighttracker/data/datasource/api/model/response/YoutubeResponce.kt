package ru.ruslan.weighttracker.data.datasource.api.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class YoutubeResponce(
    @Json(name = "kind") val kind: String,
    @Json(name = "etag") val etag: String,
    @Json(name = "nextPageToken") val nextPageToken: String,
    @Json(name = "prevPageToken") val prevPageToken: String,
    @Json(name = "pageInfo") val pageInfo: YotubePageInfo,
    @Json(name = "items") val items: List<YoutubeItems>
)