package ru.ruslan.weighttracker.data.datasource.api.retrofit

import retrofit2.http.GET
import retrofit2.http.Query
import ru.ruslan.weighttracker.data.datasource.api.model.response.YoutubeResponce

interface YoutubeApi {
    @GET("v3/playlistItems")
    suspend fun getPlaylistVideosAsync(@Query("playlistId") playlist: String,
                               @Query("pageToken") pageToken: String): YoutubeResponce
}
