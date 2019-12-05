package ru.ruslan.weighttracker.model

import com.squareup.moshi.Json

data class YoutubeModel(
    @field:Json(name = "kind") val kind: String,
    @field:Json(name = "etag") val etag: String,
    @field:Json(name = "nextPageToken") val nextPageToken: String,
    @field:Json(name = "prevPageToken") val prevPageToken: String,
    @field:Json(name = "pageInfo") val pageInfo: YoutubeModel?,
    @field:Json(name = "totalResults") val totalResult: Int,
    @field:Json(name = "resultsPerPage") val resultsPerPage: Int,
    @field:Json(name = "items") val items: List<YoutubeModel>?,
    @field:Json(name = "snippet") val snippet: YoutubeSnippet?,
    @field:Json(name = "contentDetails") val contentDetails: YoutubeContentDetails?
) {

    constructor() : this("", "", "", "", null, 0, 0, null, null, null)
}