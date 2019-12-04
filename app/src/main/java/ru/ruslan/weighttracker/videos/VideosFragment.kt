package ru.ruslan.weighttracker.videos

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_videos.*
import kotlinx.coroutines.*
import ru.ruslan.weighttracker.OnItemClickListener
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.data.source.RemoteDataSourceImpl
import ru.ruslan.weighttracker.model.YoutubeModel
import ru.ruslan.weighttracker.network.ApiFactory
import ru.ruslan.weighttracker.util.Constants
import ru.ruslan.weighttracker.util.showSnackBar
import kotlin.coroutines.CoroutineContext

class VideosFragment : VideoContract.View, Fragment(), CoroutineScope {

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    private lateinit var ctx: Context
    private var adapter: VideosAdapter? = null
    private lateinit var videoPresenter: VideoContract.VideoPresenter
    private var videosList: List<YoutubeModel> = emptyList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_videos, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videoPresenter =
            VideoPresenter(VideoInteractor(RemoteDataSourceImpl(ApiFactory.getRestClient(ctx))))
        videoPresenter.setView(this)
    }

    override fun onResume() {
        super.onResume()
        videoPresenter.getVideos(Constants.VIDEO_PLAYLIST_1)
    }

    override fun initVars() {
        adapter = VideosAdapter(ctx, videosList, object : OnItemClickListener {

            override fun itemClick(position: Int) {
                videoPresenter.videoItemClick(position)
            }

            override fun itemLongClick(position: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
        rv_videos.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
        rv_videos.adapter = adapter
    }

    override fun populateAdapter(videos: List<YoutubeModel>) {
        adapter?.addItems(videos)
    }

    override fun openVideoDetails(video: YoutubeModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showErrorToast(message: String?) {
        rv_videos.showSnackBar("Ошибка - $message")
    }

    override fun showLoadingView() {
        ll_progress.visibility = View.VISIBLE
    }

    override fun hideLoadingView() {
        ll_progress.visibility = View.GONE
    }

    companion object {
        @JvmStatic
        fun newInstance() = VideosFragment()
    }
}
