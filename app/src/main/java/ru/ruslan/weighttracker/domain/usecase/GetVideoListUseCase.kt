package ru.ruslan.weighttracker.domain.usecase

import ru.ruslan.weightracker.core.datatype.Result
import ru.ruslan.weightracker.core.datatype.ResultType
import ru.ruslan.weighttracker.domain.model.videolists.VideosEntity
import ru.ruslan.weighttracker.domain.repository.VideoListRepository
import javax.inject.Inject

class GetVideoListUseCase @Inject constructor(private val remoteRepo: VideoListRepository?) {

    interface Callback{
        fun success(videos: VideosEntity?)
        fun error(error: String)
    }

    suspend fun getVideosByPlaylist(playList: String, pageToken: String, callback: Callback) {
        var videos: Result<VideosEntity>? = null

        val result = remoteRepo?.getVideosForPlayList(playList, pageToken)
        if (result?.resultType == ResultType.SUCCESS) {
            callback.success(result.data)
        } else {
            callback.error(result?.error?.message.toString())
        }
    }
}
