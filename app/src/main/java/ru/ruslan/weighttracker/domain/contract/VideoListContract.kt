package ru.ruslan.weighttracker.domain.contract

import ru.ruslan.weighttracker.ui.videos.list.model.VideoUI

interface VideoListContract {
    interface View: BaseView{
        fun initViews()
        fun setListeners()
        fun populateAdapter(videos: List<VideoUI>)
        fun showHideLoadingView(isLoading: Boolean)
        fun isLoadingNextPages(isLoadin: Boolean)
        fun showHideLoadingInAdapter(isShow: Boolean)
        fun isLastLoadedPage(last: Boolean)
        fun errorLoading(error: String)
    }

    interface Presenter: BasePresenter<View>{
        fun downloadVideos(playList: String, pageToken: String)
        fun refreshViews()
        fun downloadNextPages()
        fun initPresenter()
        fun getCurrentPage(): Int
    }
}