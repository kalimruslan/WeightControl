package ru.ruslan.weighttracker.data.datasource.api.model.response

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class YoutubeThumbnailsDefault (@SerializedName("url") val url: String,
                                     @SerializedName("width") val width: String,
                                     @SerializedName("height") val height: String)
