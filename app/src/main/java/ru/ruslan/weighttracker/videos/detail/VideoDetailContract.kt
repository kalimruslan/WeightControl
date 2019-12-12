package ru.ruslan.weighttracker.videos.detail

import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import ru.ruslan.weighttracker.base.BaseContract
import ru.ruslan.weighttracker.base.BasePresenter

interface VideoDetailContract {
    interface View: BaseContract{
        fun initVars()
        fun runVideo(wasRestored: Boolean)
        fun failureVideoRunning(p0: YouTubePlayer.Provider?,
                                p1: YouTubeInitializationResult?)
        fun setListeners()
        fun initViews()
    }

    interface Presenter: BasePresenter<View>{
        fun init()
        fun playerInitializeSucces(wasRestored: Boolean)
        fun playerInitializeError(p0: YouTubePlayer.Provider?,
                                  p1: YouTubeInitializationResult?)
    }
}