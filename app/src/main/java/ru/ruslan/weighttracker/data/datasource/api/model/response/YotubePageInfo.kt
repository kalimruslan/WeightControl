package ru.ruslan.weighttracker.data.datasource.api.model.response

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class YotubePageInfo(
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("resultsPerPage") val resultsPerPage: Int
)
