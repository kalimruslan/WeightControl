package ru.ruslan.weighttracker.data.datasource.api

import ru.ruslan.weighttracker.core.datatype.Result
import ru.ruslan.weighttracker.data.datasource.api.exceptions.NoConnectivityException
import ru.ruslan.weighttracker.data.datasource.api.exceptions.handleNetworkExceptions
import ru.ruslan.weighttracker.data.datasource.api.model.response.YoutubeResponce
import ru.ruslan.weighttracker.data.datasource.api.retrofit.YoutubeApi
import java.lang.Exception

class VideoListNetworkDataSource(private val youtubeApiService: YoutubeApi) {
    suspend fun getVideosForPlayList(playlist: String,
                                     pageToken: String): Result<YoutubeResponce> {
        return try {
            val result = youtubeApiService.getPlaylistVideosAsync(playlist, pageToken)
            Result.success(result)
        } catch (ex: Exception){
            Result.error(handleNetworkExceptions(ex))
        } catch (ex: NoConnectivityException){
            Result.error(ex)
        }
    }
}