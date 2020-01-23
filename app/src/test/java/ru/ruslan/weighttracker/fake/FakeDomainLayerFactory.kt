package ru.ruslan.weighttracker.fake

import ru.ruslan.weighttracker.data.datasource.api.model.response.*
import ru.ruslan.weighttracker.domain.model.videolists.*
import java.util.*
import java.util.concurrent.ThreadLocalRandom


object FakeDomainLayerFactory {

    private fun randomString(): String {
        return ""
    }

    private fun randomInt(): Int {
        return 1
    }

    fun makeVideosEntity(itemsSize: Int): VideosEntity? {
        return VideosEntity(randomString(), randomInt(), randomInt(), makeVideoEntityList(itemsSize))
    }

    private fun makeVideoEntityList(itemSize: Int): List<VideoEntity> {
        val videoEntityList = mutableListOf<VideoEntity>()
        repeat(itemSize){
            videoEntityList.add(VideoEntity(makeSnippetEntity(), makeContentDetails()))
        }
        return videoEntityList
    }

    private fun makeSnippetEntity(): SnippetEntity {
        return SnippetEntity(randomString(), randomString(), makeThumbnailsEntity(), randomString() )
    }

    private fun makeContentDetails(): ContentDetailsEntity {
        return ContentDetailsEntity(randomString(), randomString())
    }

    private fun makeThumbnailsEntity(): SnippetThumbnailsEntity? {
        return SnippetThumbnailsEntity(randomString(), randomString(), randomString())
    }
}