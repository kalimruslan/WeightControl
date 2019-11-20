package ru.ruslan.weighttracker.model

import com.squareup.moshi.Json

data class YoutubeModel(
    @field:Json(name = "items") val items:List<YoutubeModel?>,
    @field:Json(name = "snippet") val snippet:YoutubeSnippet,
    @field:Json(name = "contentDetails") val contentDetails: YoutubeContentDetails)