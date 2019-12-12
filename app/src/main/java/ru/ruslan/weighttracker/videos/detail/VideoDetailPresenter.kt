package ru.ruslan.weighttracker.videos.detail

import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer

class VideoDetailPresenter : VideoDetailContract.Presenter{

    private var detailView: VideoDetailContract.View? = null

    override fun setView(view: VideoDetailContract.View) {
        detailView = view
    }

    override fun init() {
        detailView?.initVars()
        detailView?.initViews()
        detailView?.setListeners()
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun onSaveState() {

    }

    override fun onRestoreState() {
    }

    override fun playerInitializeSucces(wasRestored: Boolean) {
        detailView?.runVideo(wasRestored)
    }

    override fun playerInitializeError(p0: YouTubePlayer.Provider?,
                                       p1: YouTubeInitializationResult?) {
        detailView?.failureVideoRunning(p0, p1)
    }
}