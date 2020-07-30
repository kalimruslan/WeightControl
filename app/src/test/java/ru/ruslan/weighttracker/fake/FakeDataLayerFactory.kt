package ru.ruslan.weighttracker.fake

import ru.ruslan.weighttracker.data.datasource.api.model.response.*
import java.util.*
import java.util.concurrent.ThreadLocalRandom

object FakeDataLayerFactory {
    fun makeYoutubeResponse(itemsSize: Int): YoutubeResponce {
        return YoutubeResponce(randomString(), randomString(), randomString(), randomString(), makeYoutubePageInfo(), makeYoutubeItems(itemsSize))
    }

    private fun makeYoutubePageInfo(): YoutubePageInfo {
        return YoutubePageInfo(randomInt(), randomInt())
    }

    private fun makeYoutubeItems(itemsSize: Int): List<YoutubeItems>{
        val youtubeItems = mutableListOf<YoutubeItems>()
        repeat(itemsSize){
            youtubeItems.add(makeYoutubeItemObject())
        }
        return youtubeItems
    }

    private fun makeYoutubeItemObject(): YoutubeItems {
        return YoutubeItems(randomString(), randomString(), makeYoutubeSnippetObject(), makeYoutubeContentDetails())
    }

    private fun makeYoutubeContentDetails(): YoutubeContentDetails? {
        return YoutubeContentDetails(randomString(), randomString())
    }

    private fun makeYoutubeSnippetObject(): YoutubeSnippet? {
        return YoutubeSnippet(randomString(), randomString(), makeYoutubeThumbnails(), randomString())
    }

    private fun makeYoutubeThumbnails(): YoutubeThumbnails? {
        return YoutubeThumbnails(makeYoutubeThumbnailsDefault(), makeYoutubeThumbnailsMedium())
    }

    private fun makeYoutubeThumbnailsMedium(): YoutubeThumbnailsMedium? {
        return YoutubeThumbnailsMedium(randomString(), randomString(), randomString())
    }

    private fun makeYoutubeThumbnailsDefault(): YoutubeThumbnailsDefault? {
        return YoutubeThumbnailsDefault(randomString(), randomString(), randomString())
    }

    private fun randomString(): String {
        return ""
    }

    private fun randomInt(): Int {
        return 1
    }
}