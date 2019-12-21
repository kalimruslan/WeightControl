package ru.ruslan.weighttracker.data.datasource.api.model.response

import com.squareup.moshi.Json

data class YotubePageInfo(
    @field:Json(name = "totalResults") val totalResults: Int,
    @field:Json(name = "resultsPerPage") val resultsPerPage: Int
    )
