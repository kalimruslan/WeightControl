package ru.ruslan.weighttracker.videos

import ru.ruslan.weighttracker.base.BaseContract
import ru.ruslan.weighttracker.base.BasePresenter
import ru.ruslan.weighttracker.model.YoutubeModel

interface VideoContract {
    interface View: BaseContract {
        fun initVars()
        fun populateAdapter(videos: List<YoutubeModel>)
        fun openVideoDetails(video: YoutubeModel)
    }

    interface VideoPresenter: BasePresenter<View> {
        fun videoItemClick(position: Int)
        fun getVideos(playlist: String)
    }
}