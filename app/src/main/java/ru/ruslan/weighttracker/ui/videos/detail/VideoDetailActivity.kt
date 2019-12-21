package ru.ruslan.weighttracker.ui.videos.detail

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_video_detail.*
import ru.ruslan.weighttracker.BuildConfig
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.ui.videos.list.vm.model.VideoUI
import ru.ruslan.weighttracker.util.Constants
import ru.ruslan.weighttracker.util.showToast
import kotlin.math.abs

class VideoDetailActivity : YouTubeBaseActivity() {
    fun showHideLoadingView(isLoading: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var video: VideoUI? = null
    private var youtubePlayer: YouTubePlayer? = null

    companion object {
        const val RECOVERY_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)
        initVars()
        initViews()
        setListeners()
    }

    private fun initVars() {
        main_toolbar.setNavigationIcon(R.drawable.ic_close)
        main_toolbar.setNavigationOnClickListener { onBackPressed() }
        video = intent.getParcelableExtra(Constants.EXTRA_PARAM_VIDEO)

        collapsingToolbar.setExpandedTitleColor(Color.WHITE)
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE)
    }

    private fun initViews() {
        video?.let {
            tv_title.text = it.title
            tv_title.contentDescription = it.title
            tv_description.text = it.description
            tv_description.text = it.description
            tv_channel_name.text = it.channelTitle
            tv_published_date.text = it.publishedAt

            val glideOptions = RequestOptions()
            glideOptions.apply {
                centerCrop()
                placeholder(R.drawable.img_placeholder)
                error(R.drawable.img_placeholder)
                fallback(R.drawable.img_placeholder)
                centerCrop()
            }

            Glide.with(this)
                .load(it.url)
                .apply(glideOptions)
                .into(iv_big_banner)
        } ?: getString(R.string.text_error).showToast(this)
    }

    private fun setListeners() {
        appbar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                if (scrollRange == -1)
                    scrollRange = appBarLayout?.totalScrollRange!!

                if (abs(scrollRange + verticalOffset) < 10) {
                    collapsingToolbar.title = getString(R.string.text_collapsing_toolbar)
                    isShow = true
                } else if (isShow) {
                    collapsingToolbar.title = ""
                    isShow = true
                }

            }

        })
        youtube_view.initialize(BuildConfig.API_KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(p0: YouTubePlayer.Provider?,
                                                 player: YouTubePlayer?,
                                                 wasRestored: Boolean) {
                youtubePlayer = player
                youtubePlayer?.cueVideo(video?.videoId)
            }

            override fun onInitializationFailure(p0: YouTubePlayer.Provider?,
                                                 p1: YouTubeInitializationResult?) {
                if (p1?.isUserRecoverableError!!) {
                    p1.getErrorDialog(this@VideoDetailActivity, RECOVERY_REQUEST).show()
                } else {
                    getString(R.string.text_initialize_error).showToast(this@VideoDetailActivity)
                }
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RECOVERY_REQUEST) {
            setListeners()
        }
    }

}