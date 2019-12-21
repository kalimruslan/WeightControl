package ru.ruslan.weighttracker.domain.usecase

import ru.ruslan.weighttracker.core.datatype.Result
import ru.ruslan.weighttracker.core.datatype.ResultType
import ru.ruslan.weighttracker.domain.repository.VideoListRepository
import ru.ruslan.weighttracker.domain.model.videolists.VideosEntity

class GetVideoListUseCase(private val remoteRepo: VideoListRepository?) {

    suspend fun getVideosByPlaylist(playList: String, pageToken: String): Result<VideosEntity>? {
        var videos: Result<VideosEntity>? = null

        val result = remoteRepo?.getVideosForPlayList(playList, pageToken)
        if (result?.resultType == ResultType.SUCCESS) {
            result.data?.let {
                videos = Result.success(it)
            }
        } else {
            videos = Result.error(result?.error)
        }

        return videos
    }
}
