package ru.ruslan.weighttracker.ui

import ru.ruslan.weightracker.core.BaseMapper
import ru.ruslan.weighttracker.data.datasource.localdb.model.ProfileLocal
import ru.ruslan.weighttracker.domain.model.PhotoDataEntity
import ru.ruslan.weighttracker.domain.model.profile.ProfileEntity
import ru.ruslan.weighttracker.domain.model.videolists.VideoEntity
import ru.ruslan.weighttracker.ui.home.HomeUI
import ru.ruslan.weighttracker.ui.profile.vm.model.ProfileUI
import ru.ruslan.weighttracker.ui.videos.list.vm.model.VideoUI
import kotlin.math.round

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
                imt = round(it.currentIMT).toString(),
                goalWeight = it.goalWeight,
                measuresMap = it.measuresMap
            )
        }
    }
}

object ProfileUIToEntityMapper: BaseMapper<ProfileUI, ProfileEntity>{
    override fun map(type: ProfileUI?): ProfileEntity? {
        return type?.let {
            ProfileEntity(
                fio = it.fio!!,
                dateBirth = "05.02.1987",
                currentWeight = it.currentWeight,
                currentHeight = it.currentHeight,
                sex = it.sex,
                goalWeight = it.goalWeight,
                weightEntity = null,
                photoEntity = null
            )
        }
    }
}

object PhotoDataEntityToHomeUIMapper: BaseMapper<PhotoDataEntity, HomeUI>{
    override fun map(type: PhotoDataEntity?): HomeUI? {
        return type?.let {
            HomeUI(photoDate = it.photoDate, photoPath = it.photoPath, weightOnPhoto = it.weightOnPhoto.toString())
        }
    }
}