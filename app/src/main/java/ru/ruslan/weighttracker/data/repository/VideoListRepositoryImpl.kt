package ru.ruslan.weighttracker.data.repository

import ru.ruslan.weighttracker.core.datatype.Result
import ru.ruslan.weighttracker.core.datatype.ResultType
import ru.ruslan.weighttracker.data.datasource.api.VideoListNetworkDataSource
import ru.ruslan.weighttracker.videos.list.domain.VideoListRepository
import ru.ruslan.weighttracker.data.repository.mapper.VideoListRepositoryMapper
import ru.ruslan.weighttracker.videos.list.domain.model.VideosEntity

class VideoListRepositoryImpl(private val videoListNetworkDataSource: VideoListNetworkDataSource) :
    VideoListRepository {

    override suspend fun getVideosForPlayList(playlist: String,
                                              pageToken: String): Result<VideosEntity>? {
        val result: Result<VideosEntity>?
        val resultYoutubeModel =
            videoListNetworkDataSource.getVideosForPlayList(playlist, pageToken)

        result = if (resultYoutubeModel.resultType == ResultType.SUCCESS) {
            if (resultYoutubeModel.data?.items!!.isNotEmpty()) {
                Result.success(VideoListRepositoryMapper.map(resultYoutubeModel.data))
            } else {
                Result.error(resultYoutubeModel.error)
            }
        } else {
            Result.error(resultYoutubeModel.error)
        }
        return result
    }
}