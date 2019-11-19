package ru.ruslan.weighttracker.model

import com.squareup.moshi.Json

data class YoutubeThumbnails(
    @field:Json(name = "default") val default: YoutubeThumbnails?,
    @field:Json(name = "url") val url: String,
    @field:Json(name = "width") val width: String,
    @field:Json(name = "height") val height: String
)
