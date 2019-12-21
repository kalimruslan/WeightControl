package ru.ruslan.weighttracker.ui.videos.list.vm.mapper

import ru.ruslan.weightracker.core.BaseMapper
import ru.ruslan.weighttracker.domain.model.videolists.VideoEntity
import ru.ruslan.weighttracker.ui.videos.list.vm.model.VideoUI

object VideosEntityToUiMapper:
    ru.ruslan.weightracker.core.BaseMapper<List<VideoEntity>, List<VideoUI>> {
    override fun map(type: List<VideoEntity>?): List<VideoUI> {
        return type?.map { video ->
            VideoUI(
                title = video.snippet.title,
                description = video.snippet.description,
                url = video.snippet.thumbnailsEntity?.url!!,
                channelTitle = video.snippet.channelTitle,
                publishedAt = video.contentDetails.publishedAt,
                videoId = video.contentDetails.videoId
            )
        } ?: listOf()
    }
}