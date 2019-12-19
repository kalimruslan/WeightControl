package ru.ruslan.weighttracker.data.repository.mapper

import ru.ruslan.weighttracker.core.BaseMapper
import ru.ruslan.weighttracker.data.datasource.api.model.response.YotubeResponce
import ru.ruslan.weighttracker.data.datasource.localdb.model.PhotoLocal
import ru.ruslan.weighttracker.data.datasource.localdb.model.ProfileLocal
import ru.ruslan.weighttracker.data.datasource.localdb.model.WeightLocal
import ru.ruslan.weighttracker.home.domain.model.PhotoEntity
import ru.ruslan.weighttracker.home.domain.model.ProfileEntity
import ru.ruslan.weighttracker.home.domain.model.WeightEntity
import ru.ruslan.weighttracker.videos.list.domain.model.*

object ApiToEntityMapper : BaseMapper<YotubeResponce, VideosEntity> {
    override fun map(type: YotubeResponce?): VideosEntity? {
        var videosEntity: VideosEntity? = null
        if (type != null) {
            videosEntity = VideosEntity(
                type.nextPageToken,
                type.pageInfo?.resultsPerPage!!,
                type.pageInfo.totalResult,
                getVideosList(type)
            )
        }
        return videosEntity
    }

    private fun getVideosList(type: YotubeResponce): List<VideoEntity> {
        return type.items.map { item ->
            val snippetThumbnailsEntity = SnippetThumbnailsEntity(
                url = item.snippet?.thumbnails?.medium?.url!!,
                width = item.snippet.thumbnails.medium.width,
                height = item.snippet.thumbnails.medium.height
            )
            val snippetEntity = SnippetEntity(
                title = item.snippet.title,
                description = item.snippet.description,
                thumbnailsEntity = snippetThumbnailsEntity,
                channelTitle = item.snippet.channelTitle
            )
            val contentDetailsEntity = ContentDetailsEntity(
                videoId = item.contentDetails?.videoId!!,
                publishedAt = item.contentDetails.publishedAt
            )
            VideoEntity(snippet = snippetEntity, contentDetails = contentDetailsEntity)
        }
    }
}

object ProfileEntityToLocalMapper : BaseMapper<ProfileEntity, ProfileLocal> {
    override fun map(type: ProfileEntity?): ProfileLocal? {
        var profileLocal: ProfileLocal? = null
        type?.let {
            val weightLocal =
                if (it.weightEntity != null) {
                    WeightLocal(
                        profileId = it.weightEntity?.profileId!!,
                        weight = it.weightEntity.weight, weightDate = it.weightEntity.weightDate
                    )
                } else null
            val photoLocal =
                if (it.photoEntity != null) {
                    PhotoLocal(
                        profileId = it.photoEntity?.profileId!!,
                        photoUrl = it.photoEntity.photoUrl,
                        photoDate = it.photoEntity.photoDate
                    )
                } else null
            profileLocal = ProfileLocal(
                fio = it.fio,
                dateBirth = it.dateBirth,
                currWeight = it.currentWeight,
                currHeight = it.currentHeight,
                currIMT = it.currentIMT,
                goalWeight = it.goalWeight,
                weightLocal = weightLocal,
                photoLocal = photoLocal
            )
        }
        return profileLocal
    }
}

object WeightEntityToLocalMapper : BaseMapper<WeightEntity, WeightLocal> {
    override fun map(type: WeightEntity?): WeightLocal? {
        var weightLocal: WeightLocal? = null
        type?.let {
            weightLocal = WeightLocal(
                profileId = it.profileId,
                weight = it.weight,
                weightDate = it.weightDate
            )
        }
        return weightLocal
    }
}

object PhotoEntityToLocalMapper : BaseMapper<PhotoEntity, PhotoLocal> {
    override fun map(type: PhotoEntity?): PhotoLocal? {
        var photoLocal: PhotoLocal? = null
        type?.let {
            photoLocal = PhotoLocal(
                profileId = it.profileId,
                photoUrl =  it.photoUrl,
                photoDate =  it.photoDate
            )
        }
        return photoLocal
    }
}

object LocalToEntittMapper : BaseMapper<ProfileLocal, ProfileEntity> {
    override fun map(type: ProfileLocal?): ProfileEntity? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}