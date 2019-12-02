package ru.ruslan.weighttracker.videos

import kotlinx.coroutines.*
import ru.ruslan.weighttracker.model.YoutubeModel
import ru.ruslan.weighttracker.network.Result
import kotlin.coroutines.CoroutineContext

class VideoPresenter(private val videoIntreractor: VideoInteractor) : VideoContract.VideoPresenter {

    private var job = Job()
    private val coroutineIOContext: CoroutineContext = job + Dispatchers.IO
    private var videosView: VideoContract.View? = null

    override fun setView(view: VideoContract.View) {
        videosView = view
    }

    override fun getVideos(playlist: String) {
        job = CoroutineScope(coroutineIOContext).launch {
            withContext(Dispatchers.Main) {
                videosView?.showLoadingView()
            }
            when (val result = videoIntreractor.getVideosByPlaylist(playlist)) {
                is Result.Success<YoutubeModel> -> {
                    withContext(Dispatchers.Main) {
                        videosView?.populateAdapter(result.data.items!!)
                        videosView?.hideLoadingView()
                    }
                }
                is Result.Error -> {
                    videosView?.showErrorToast(result.exception.message)
                    withContext(Dispatchers.Main) {
                        videosView?.hideLoadingView()
                    }
                }
            }
        }
    }

    override fun videoItemClick(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}