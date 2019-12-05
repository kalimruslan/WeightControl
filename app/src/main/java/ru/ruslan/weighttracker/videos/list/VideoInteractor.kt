package ru.ruslan.weighttracker.videos.list

import ru.ruslan.weighttracker.data.RemoteDataSource

class VideoInteractor(private val remoteRepo: RemoteDataSource?) {

    suspend fun getVideosByPlaylist(playList: String, pageToken: String) =
        remoteRepo?.getVideosForPlayList(playList, pageToken)
}
