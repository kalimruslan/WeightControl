package ru.ruslan.weighttracker.data.repository.mapper

import ru.ruslan.weighttracker.core.BaseMapper
import ru.ruslan.weighttracker.data.datasource.api.model.response.YotubeResponce
import ru.ruslan.weighttracker.videos.list.domain.model.*

object ApiToEntityMapper: BaseMapper<YotubeResponce, VideosEntity>{
    override fun map(type: YotubeResponce?): VideosEntity? {
        var videosEntity: VideosEntity? = null
        if(type != null){
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
                publishedAt = item.contentDetails.publishedAt)
            VideoEntity(snippet = snippetEntity, contentDetails = contentDetailsEntity)
        }
    }
}