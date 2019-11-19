package ru.ruslan.weighttracker.model

import com.squareup.moshi.Json

data class YoutubeContentDetails(
    @field:Json(name = "videoId") val videoId:String,
    @field:Json(name = "videoPublishedAt") val publishedAt:String)
