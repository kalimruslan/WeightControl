package ru.ruslan.weighttracker.data.repository

import ru.ruslan.weightracker.core.datatype.Result
import ru.ruslan.weightracker.core.datatype.ResultType
import ru.ruslan.weighttracker.data.datasource.api.VideoListNetworkDataSource
import ru.ruslan.weighttracker.data.repository.mapper.ApiToEntityMapper
import ru.ruslan.weighttracker.domain.repository.VideoListRepository
import ru.ruslan.weighttracker.domain.model.videolists.VideosEntity
import javax.inject.Inject

class VideoListRepositoryImpl @Inject constructor(private val videoListNetworkDataSource: VideoListNetworkDataSource) :
    VideoListRepository {

    override suspend fun getVideosForPlayList(playlist: String,
                                              pageToken: String): Result<VideosEntity>? {
        val result: Result<VideosEntity>?
        val resultYoutubeModel =
            videoListNetworkDataSource.getVideosForPlayList(playlist, pageToken)

        result = if (resultYoutubeModel.resultType == ResultType.SUCCESS) {
            if (resultYoutubeModel.data?.items!!.isNotEmpty()) {
                Result.success(ApiToEntityMapper.map(resultYoutubeModel.data))
            } else {
                Result.error(resultYoutubeModel.error)
            }
        } else {
            Result.error(resultYoutubeModel.error)
        }
        return result
    }
}