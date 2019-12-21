package ru.ruslan.weighttracker.data.datasource.api.model.response

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class YoutubeItems(@SerializedName("kind") val kind: String,
                        @SerializedName("etag") val etag: String,
                        @SerializedName("snippet") val snippet: YoutubeSnippet?,
                        @SerializedName("contentDetails") val contentDetails: YoutubeContentDetails?)
