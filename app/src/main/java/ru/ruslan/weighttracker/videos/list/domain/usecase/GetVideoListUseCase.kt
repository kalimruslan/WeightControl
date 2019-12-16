package ru.ruslan.weighttracker.videos.list.domain.usecase

import android.util.Log
import ru.ruslan.weighttracker.core.datatype.Result
import ru.ruslan.weighttracker.core.datatype.ResultType
import ru.ruslan.weighttracker.util.printLog
import ru.ruslan.weighttracker.videos.list.domain.VideoListRepository
import ru.ruslan.weighttracker.videos.list.domain.model.VideosEntity

class GetVideoListUseCase(private val remoteRepo: VideoListRepository?) {

    suspend fun getVideosByPlaylist(playList: String, pageToken: String): Result<VideosEntity>?{
        var videos: Result<VideosEntity>? = null

        val result = remoteRepo?.getVideosForPlayList(playList, pageToken)
        if(result?.resultType == ResultType.SUCCESS){
            result.data?.let {
                videos = Result.success(it)
            }
        }
        else{
            videos = Result.error(result?.error)
        }

        return videos
    }
}
