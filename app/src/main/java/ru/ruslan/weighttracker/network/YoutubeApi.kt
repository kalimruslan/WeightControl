package ru.ruslan.weighttracker.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query
import ru.ruslan.weighttracker.model.YoutubeModel

interface YoutubeApi {
    @GET("v3/playlistItems")
    suspend fun getPlaylistVideosAsync(playlist: String): Deferred<YoutubeModel>
}
