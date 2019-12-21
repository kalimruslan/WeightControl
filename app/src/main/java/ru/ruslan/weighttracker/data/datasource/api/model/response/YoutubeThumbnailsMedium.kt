package ru.ruslan.weighttracker.data.datasource.api.model.response

import com.squareup.moshi.Json

data class YoutubeThumbnailsMedium(@field:Json(name = "url") val url: String,
                                   @field:Json(name = "width") val width: String,
                                   @field:Json(name = "height") val height: String)
