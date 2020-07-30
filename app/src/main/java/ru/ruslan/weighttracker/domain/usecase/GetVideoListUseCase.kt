package ru.ruslan.weighttracker.domain.usecase

import ru.ruslan.weightracker.core.datatype.Result
import ru.ruslan.weightracker.core.datatype.ResultType
import ru.ruslan.weighttracker.domain.model.videolists.VideosEntity
import ru.ruslan.weighttracker.domain.repository.VideoListRepository
import javax.inject.Inject

class GetVideoListUseCase @Inject constructor(private val remoteRepo: VideoListRepository?) {

    suspend fun getVideosByPlaylist(playList: String, pageToken: String,
                                    funcSuccess: (VideosEntity) -> Unit,
                                    funcError: (String) -> Unit) {
        val result = remoteRepo?.getVideosForPlayList(playList, pageToken)
        if (result?.resultType == ResultType.SUCCESS) {
            result.data?.let { funcSuccess(it) }
        } else {
            funcError(result?.error?.message.toString())
        }
    }
}
