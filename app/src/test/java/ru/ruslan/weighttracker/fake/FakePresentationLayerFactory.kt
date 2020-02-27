package ru.ruslan.weighttracker.fake

import ru.ruslan.weighttracker.ui.videos.list.vm.model.VideoUI

object FakePresentationLayerFactory {

    fun makeVideoUI(itemsSize: Int): List<VideoUI>? {
        val list = mutableListOf<VideoUI>()
        repeat(itemsSize){
            list.add(VideoUI())
        }
        return list
    }

}