package ru.ruslan.weighttracker.videos.detail

import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_video_detail.*
import kotlinx.android.synthetic.main.item_video.view.*
import ru.ruslan.weighttracker.BuildConfig
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.poko.YoutubeModel
import ru.ruslan.weighttracker.util.Constants
import ru.ruslan.weighttracker.util.showToast
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T

class VideoDetailActivity : YouTubeBaseActivity(), VideoDetailContract.View {

    private var youtubeModel: YoutubeModel? = null
    private var youtubePlayer: YouTubePlayer? = null
    private var presenter: VideoDetailContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)
        presenter = VideoDetailPresenter()
        presenter?.setView(this)
        presenter?.init()
    }

    override fun onStart() {
        super.onStart()
        presenter?.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter?.onStop()
    }

    override fun initVars() {
        youtubeModel = intent.getParcelableExtra(Constants.INTENT_PARAM_YOUTUBE_MODEL)
    }

    override fun initViews(){
        youtubeModel?.let {
            tv_title.text = it.snippet?.title
            tv_description.text = it.snippet?.description
            tv_channel_name.text = it.snippet?.channelTitle
            tv_published_date.text = it.contentDetails?.publishedAt

            val options = RequestOptions()
            options.centerCrop()
            options.placeholder(R.drawable.img_placeholder)
            options.error(R.drawable.img_placeholder)
            options.fallback(R.drawable.img_placeholder)
            options.centerCrop()

            Glide.with(this)
                .load(it.snippet?.thumbnails?.medium?.url)
                .apply(options)
                .into(iv_big_banner)

            Glide.with(this)
                .load(it.snippet?.thumbnails?.default?.url)
                .apply(options)
                .into(iv_poster)
        } ?: getString(R.string.text_error).showToast(this)
    }

    override fun setListeners() {
        youtube_view.initialize(BuildConfig.API_KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(p0: YouTubePlayer.Provider?,
                                                 player: YouTubePlayer?,
                                                 wasRestored: Boolean) {
                youtubePlayer = player
                presenter?.playerInitializeSucces(wasRestored)
            }

            override fun onInitializationFailure(p0: YouTubePlayer.Provider?,
                                                 p1: YouTubeInitializationResult?) {
                presenter?.playerInitializeError()
            }
        })

    }

    override fun runVideo(wasRestored: Boolean) {
        youtubePlayer?.cueVideo(youtubeModel?.contentDetails?.videoId)
    }

    override fun failureVideoRunning() =
        getString(R.string.text_initialize_error).showToast(this@VideoDetailActivity)

    override fun showErrorToast(message: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoadingView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoadingView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}