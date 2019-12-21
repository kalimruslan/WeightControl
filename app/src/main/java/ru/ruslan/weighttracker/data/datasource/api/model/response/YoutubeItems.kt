package ru.ruslan.weighttracker.data.datasource.api.model.response

import com.squareup.moshi.Json

data class YoutubeItems(@field:Json(name = "kind") val kind: String,
                        @field:Json(name = "etag") val etag: String,
                        @field:Json(name = "snippet") val snippet: YoutubeSnippet?,
                        @field:Json(name = "contentDetails") val contentDetails: YoutubeContentDetails?)
