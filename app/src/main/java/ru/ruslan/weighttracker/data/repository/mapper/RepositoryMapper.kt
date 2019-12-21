package ru.ruslan.weighttracker.data.repository.mapper

import ru.ruslan.weightracker.core.BaseMapper
import ru.ruslan.weighttracker.data.datasource.api.model.response.YoutubeResponce
import ru.ruslan.weighttracker.data.datasource.localdb.model.PhotoLocal
import ru.ruslan.weighttracker.data.datasource.localdb.model.ProfileLocal
import ru.ruslan.weighttracker.data.datasource.localdb.model.WeightLocal
import ru.ruslan.weighttracker.domain.model.videolists.*
import ru.ruslan.weighttracker.domain.model.profile.PhotoEntity
import ru.ruslan.weighttracker.domain.model.profile.ProfileEntity
import ru.ruslan.weighttracker.domain.model.profile.WeightEntity

object ApiToEntityMapper :
    ru.ruslan.weightracker.core.BaseMapper<YoutubeResponce, VideosEntity> {
    override fun map(type: YoutubeResponce?): VideosEntity? {
        var videosEntity: VideosEntity? = null
        if (type != null) {
            videosEntity = VideosEntity(
                type.nextPageToken,
                type.pageInfo?.resultsPerPage!!,
                type.pageInfo.totalResults,
                getVideosList(type)
            )
        }
        return videosEntity
    }

    private fun getVideosList(type: YoutubeResponce): List<VideoEntity> {
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
                publishedAt = item.contentDetails.videoPublishedAt
            )
            VideoEntity(snippet = snippetEntity, contentDetails = contentDetailsEntity)
        }
    }
}

object ProfileEntityToLocalMapper :
    ru.ruslan.weightracker.core.BaseMapper<ProfileEntity, ProfileLocal> {
    override fun map(type: ProfileEntity?): ProfileLocal? {
        var profileLocal: ProfileLocal? = null
        type?.let {
            val weightLocal =
                if (it.weightEntity != null) {
                    WeightLocal(
                        profileId = it.weightEntity.profileId,
                        weight = it.weightEntity.weight, weightDate = it.weightEntity.weightDate
                    )
                } else null
            val photoLocal =
                if (it.photoEntity != null) {
                    PhotoLocal(
                        profileId = it.photoEntity.profileId,
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

object WeightEntityToLocalMapper :
    ru.ruslan.weightracker.core.BaseMapper<WeightEntity, WeightLocal> {
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

object PhotoEntityToLocalMapper :
    ru.ruslan.weightracker.core.BaseMapper<PhotoEntity, PhotoLocal> {
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

object LocalToEntittMapper :
    ru.ruslan.weightracker.core.BaseMapper<ProfileLocal, ProfileEntity> {
    override fun map(type: ProfileLocal?): ProfileEntity? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}