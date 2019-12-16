package ru.ruslan.weighttracker.videos.list.domain.usecase

import ru.ruslan.weighttracker.videos.list.domain.VideoListRepository

class GetVideoListUseCase(private val remoteRepo: VideoListRepository?) {

    suspend fun getVideosByPlaylist(playList: String, pageToken: String) =
        remoteRepo?.getVideosForPlayList(playList, pageToken)
}
