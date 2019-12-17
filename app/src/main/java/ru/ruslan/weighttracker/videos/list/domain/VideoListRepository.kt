package ru.ruslan.weighttracker.videos.list.domain

import ru.ruslan.weighttracker.data.datasource.api.model.response.YoutubeModel
import ru.ruslan.weighttracker.core.datatype.Result
import ru.ruslan.weighttracker.videos.list.domain.model.VideosEntity

interface VideoListRepository {
    suspend fun getVideosForPlayList(playlist: String, pageToken: String): Result<VideosEntity>?
}