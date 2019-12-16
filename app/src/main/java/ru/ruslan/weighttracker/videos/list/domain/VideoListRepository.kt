package ru.ruslan.weighttracker.videos.list.domain

import ru.ruslan.weighttracker.videos.list.domain.model.YoutubeModel
import ru.ruslan.weighttracker.core.datatype.Result

interface VideoListRepository {
    suspend fun getVideosForPlayList(playlist: String, pageToken: String): Result<YoutubeModel>
}