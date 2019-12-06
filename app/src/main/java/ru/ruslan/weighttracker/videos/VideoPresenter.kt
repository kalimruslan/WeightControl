package ru.ruslan.weighttracker.videos

import kotlinx.coroutines.*
import ru.ruslan.weighttracker.model.YoutubeModel
import ru.ruslan.weighttracker.network.Result
import ru.ruslan.weighttracker.util.Constants
import kotlin.coroutines.CoroutineContext

class VideoPresenter(private val videoIntreractor: VideoInteractor) : VideoContract.VideoPresenter {

    private var job = Job()
    private val coroutineIOContext: CoroutineContext = job + Dispatchers.IO
    private var videosView: VideoContract.View? = null

    private var currentPage = 0
    private var totalPage = 0
    private var nextPageToken: String = ""

    override fun setView(view: VideoContract.View) {
        videosView = view
        videosView?.initVars()
    }

    override fun getVideos(playlist: String, pageToken:String) {
        job = CoroutineScope(coroutineIOContext).launch {
            withContext(Dispatchers.Main) {
                if(pageToken.isEmpty())
                    videosView?.showLoadingView()
            }
            when (val result = videoIntreractor.getVideosByPlaylist(playlist, pageToken)) {
                is Result.Success<YoutubeModel> -> {
                    withContext(Dispatchers.Main) {
                        videosView?.populateAdapter(result.data)
                        nextPageToken = result.data.nextPageToken
                        totalPage = result.data.pageInfo?.totalResult!! / result.data.pageInfo.resultsPerPage
                        videosView?.isLoadingNextPages(false)
                        if(pageToken.isEmpty()) videosView?.hideLoadingView()
                    }
                }
                is Result.Error -> {
                    videosView?.showErrorToast(result.exception.message)
                    withContext(Dispatchers.Main) {
                        if(pageToken.isEmpty()) videosView?.hideLoadingView()
                    }
                }
            }
        }
    }

    override fun needNextPages() {
        videosView?.isLoadingNextPages(true)
        videosView?.incrementCurrentPageBeforeLoading()
        getVideos(Constants.VIDEO_PLAYLIST_1, nextPageToken)
        if(currentPage < totalPage){
            videosView?.showHideLoadingInAdapter(true)
        }
        else{
            videosView?.isLastLoadedPage(true)
        }
    }

    override fun refreshData() {
        videosView?.resetCurrentPage()
        videosView?.isLastLoadedPage(false)
        videosView?.clearRecyclerItems()
        nextPageToken = ""
        getVideos(Constants.VIDEO_PLAYLIST_1, nextPageToken)
    }

    override fun videoItemClick(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}