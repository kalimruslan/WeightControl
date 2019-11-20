package ru.ruslan.weighttracker.network

import retrofit2.Response
import retrofit2.http.GET
import ru.ruslan.weighttracker.model.YoutubeModel

interface YoutubeApi {
    @GET("playlistItems")
    suspend fun getPlaylistVideos(): Response<YoutubeModel>
}
