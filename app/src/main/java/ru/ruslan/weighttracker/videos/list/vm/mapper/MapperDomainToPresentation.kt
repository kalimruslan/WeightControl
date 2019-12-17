package ru.ruslan.weighttracker.videos.list.vm.mapper

import ru.ruslan.weighttracker.core.BaseMapper
import ru.ruslan.weighttracker.videos.list.domain.model.VideoEntity
import ru.ruslan.weighttracker.videos.list.vm.model.VideoUI

object VideosEntityToUiMapper: BaseMapper<List<VideoEntity>, List<VideoUI>> {
    override fun map(type: List<VideoEntity>?): List<VideoUI> {
        return type?.map { video ->
            VideoUI(
                title = video.snippet.title,
                description = video.snippet.description,
                url = video.snippet.thumbnailsEntity?.url!!
            )
        } ?: listOf()
    }
}