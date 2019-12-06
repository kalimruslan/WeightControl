package ru.ruslan.weighttracker.model

import com.squareup.moshi.Json

data class YoutubeSnippet(
    @field:Json(name = "title") val title: String,
    @field:Json(name = "description") val description: String,
    @field:Json(name = "thumbnails") val thumbnails: YoutubeThumbnails
)
