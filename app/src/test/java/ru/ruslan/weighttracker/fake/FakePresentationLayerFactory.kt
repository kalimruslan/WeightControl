package ru.ruslan.weighttracker.fake

import ru.ruslan.weighttracker.data.datasource.api.model.response.*
import ru.ruslan.weighttracker.domain.model.videolists.*
import ru.ruslan.weighttracker.ui.videos.list.vm.model.VideoUI
import java.util.*
import java.util.concurrent.ThreadLocalRandom


object FakePresentationLayerFactory {

    fun makeVideoUI(itemsSize: Int): List<VideoUI>? {
        val list = mutableListOf<VideoUI>()
        repeat(itemsSize){
            list.add(VideoUI())
        }
        return list
    }

}