package ru.ruslan.weighttracker.videos

import ru.ruslan.weighttracker.base.BaseContract
import ru.ruslan.weighttracker.base.BasePresenter
import ru.ruslan.weighttracker.model.YoutubeModel

interface VideoContract {
    interface View: BaseContract {
        fun initVars()
        fun populateAdapter(videos: YoutubeModel)
        fun openVideoDetails(video: YoutubeModel)
        fun showHideLoadingInAdapter(isShow: Boolean)
        fun isLoadingNextPages(loading: Boolean)
        fun isLastLoadedPage(last: Boolean)
        fun incrementCurrentPageBeforeLoading()
        fun resetCurrentPage()
        fun clearRecyclerItems()
    }

    interface VideoPresenter: BasePresenter<View> {
        fun videoItemClick(position: Int)
        fun getVideos(playlist: String, pageToken: String)
        fun needNextPages()
        fun refreshData()
    }
}