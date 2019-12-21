package ru.ruslan.weighttracker.domain.repository

import ru.ruslan.weightracker.core.datatype.Result
import ru.ruslan.weighttracker.domain.model.videolists.VideosEntity

interface VideoListRepository {
    suspend fun getVideosForPlayList(playlist: String, pageToken: String): Result<VideosEntity>?
}