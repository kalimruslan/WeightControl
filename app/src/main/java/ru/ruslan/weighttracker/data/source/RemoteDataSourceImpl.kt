package ru.ruslan.weighttracker.data.source

import retrofit2.HttpException
import ru.ruslan.weighttracker.data.RemoteDataSource
import ru.ruslan.weighttracker.model.YoutubeModel
import ru.ruslan.weighttracker.network.NoConnectivityException
import ru.ruslan.weighttracker.network.Result
import ru.ruslan.weighttracker.network.YoutubeApi

class RemoteDataSourceImpl(private val youtubeApi: YoutubeApi): RemoteDataSource {
    override suspend fun getVideosForPlayList(playlist: String, pageToken: String): Result<YoutubeModel> {
        return try {
            val result = youtubeApi.getPlaylistVideosAsync(playlist, pageToken).await()
            Result.Success(result)
        } catch (e: HttpException){
            Result.Error(e)
        } catch (e: NoConnectivityException){
            Result.Error(e)
        } catch (e: Throwable){
            Result.Error(e)
        }
    }
}