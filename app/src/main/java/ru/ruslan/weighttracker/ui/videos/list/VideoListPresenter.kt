package ru.ruslan.weighttracker.ui.videos.list

import kotlinx.coroutines.*
import ru.ruslan.weighttracker.dagger.scope.VideoListScope
import ru.ruslan.weighttracker.domain.contract.VideoListContract
import ru.ruslan.weighttracker.domain.model.videolists.VideosEntity
import ru.ruslan.weighttracker.domain.usecase.GetVideoListUseCase
import ru.ruslan.weighttracker.ui.VideosEntityToUiMapper
import ru.ruslan.weighttracker.ui.util.Constants
import javax.inject.Inject

@VideoListScope
class VideoListPresenter @Inject constructor(private val getVideoListUseCase: GetVideoListUseCase) :
    VideoListContract.Presenter {

    private lateinit var view: VideoListContract.View
    private var currentPage = 1
    private var totalPage = 0
    private var nextPageToken = ""
    private val coroutineScope = CoroutineScope(Job() + Dispatchers.IO)

    override fun setView(view: VideoListContract.View) {
        this.view = view
    }

    override fun initPresenter() {
        view.initViews()
        view.setListeners()
        downloadVideos(Constants.VIDEO_PLAYLIST, "")
    }

    override fun downloadVideos(playList: String, pageToken: String) {
        nextPageToken = pageToken
        if (pageToken.isEmpty())
            view.showHideLoadingView(true)
        coroutineScope.launch {
            getVideoListUseCase.getVideosByPlaylist(playList, pageToken, { videos ->
                launch(Dispatchers.Main) {
                    view.showHideLoadingView(false)
                    nextPageToken = videos.nextPageToken
                    totalPage = videos.totalResult / videos.resultPerPage

                    val videosUI = VideosEntityToUiMapper.map(videos.items)

                    if (videosUI.isNotEmpty())
                        view.populateAdapter(videosUI)
                }
            }, { error ->
                launch(Dispatchers.Main) {
                    view.showHideLoadingView(false)
                    view.errorLoading(error)
                }
            })
        }
    }

    override fun refreshViews() {
        currentPage = 1
        view.isLastLoadedPage(false)
        downloadVideos(Constants.VIDEO_PLAYLIST, "")
        if (currentPage < totalPage)
            view.showHideLoadingInAdapter(true)
        else
            view.isLastLoadedPage(true)
    }

    override fun downloadNextPages() {
        view.isLastLoadedPage(true)
        currentPage++
        downloadVideos(Constants.VIDEO_PLAYLIST, nextPageToken)
    }

    override fun getCurrentPage(): Int = currentPage
}