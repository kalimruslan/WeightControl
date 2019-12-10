package ru.ruslan.weighttracker.videos.detail

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

    override fun playerInitializeSucces(wasRestored: Boolean) {
        detailView?.runVideo(wasRestored)
    }

    override fun playerInitializeError() {
        detailView?.failureVideoRunning()
    }
}