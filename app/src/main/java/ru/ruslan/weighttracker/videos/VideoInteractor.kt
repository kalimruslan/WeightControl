package ru.ruslan.weighttracker.videos

import ru.ruslan.weighttracker.data.RemoteDataSource

class VideoInteractor(private val remoteRepo: RemoteDataSource?) {

    suspend fun getVideosByPlaylist(playList: String) =
        remoteRepo?.getVideosForPlayList(playList)

}
