package ru.ruslan.weighttracker.ui

import ru.ruslan.weightracker.core.BaseMapper
import ru.ruslan.weighttracker.data.datasource.localdb.model.ProfileLocal
import ru.ruslan.weighttracker.domain.model.profile.ProfileEntity
import ru.ruslan.weighttracker.domain.model.videolists.VideoEntity
import ru.ruslan.weighttracker.ui.profile.vm.model.ProfileUI
import ru.ruslan.weighttracker.ui.videos.list.vm.model.VideoUI

object VideosEntityToUiMapper : BaseMapper<List<VideoEntity>, List<VideoUI>> {
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

object ProfileEntityToUIMapper : BaseMapper<ProfileEntity, ProfileUI> {
    override fun map(type: ProfileEntity?): ProfileUI? {
        return type?.let {
            ProfileUI(
                fio = it.fio,
                currentWeight = it.currentWeight,
                currentHeight = it.currentHeight,
                sex = it.sex,
                goalWeight = it.goalWeight,
                measuresMap = it.measuresMap
            )
        }
    }

}