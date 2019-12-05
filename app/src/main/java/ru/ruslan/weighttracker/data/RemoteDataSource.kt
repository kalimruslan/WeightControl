package ru.ruslan.weighttracker.data

import ru.ruslan.weighttracker.model.YoutubeModel
import ru.ruslan.weighttracker.network.Result

interface RemoteDataSource {
    suspend fun getVideosForPlayList(playlist: String, pageToken: String): Result<YoutubeModel>
}