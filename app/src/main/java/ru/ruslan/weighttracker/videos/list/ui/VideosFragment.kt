package ru.ruslan.weighttracker.videos.list.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_videos.*
import kotlinx.coroutines.*
import ru.ruslan.weighttracker.OnItemClickListener
import ru.ruslan.weighttracker.PaginationScrollListener
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.data.datasource.api.model.response.YoutubeModel
import ru.ruslan.weighttracker.data.datasource.api.retrofit.ApiFactory
import ru.ruslan.weighttracker.util.Constants
import ru.ruslan.weighttracker.util.showSnackBar
import ru.ruslan.weighttracker.videos.detail.VideoDetailActivity
import ru.ruslan.weighttracker.videos.list.VideoContract
import ru.ruslan.weighttracker.videos.list.domain.usecase.GetVideoListUseCase
import ru.ruslan.weighttracker.videos.list.vm.VideoListViewModel
import ru.ruslan.weighttracker.videos.list.vm.model.VideoUI
import kotlin.coroutines.CoroutineContext

class VideosFragment : VideoContract.View, Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var ctx: Context
    private var adapter: VideosAdapter? = null
    private var videosList: MutableList<VideoUI> = mutableListOf()
    private var currentPage = 1
    private var isLastPage = false
    private var isLoading = false

    private var viewModel: VideoListViewModel? = null

    companion object {
        @JvmStatic
        fun newInstance() = VideosFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_videos, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVars()
        observerLiveData()
    }

    private fun observerLiveData() {
        viewModel?.videosLiveData?.observe(this, Observer(::populateAdapterRight))
        viewModel?.isLoadingLiveData?.observe(this, Observer(::showHideLoadingView))
    }

    override fun initVars() {
        viewModel = ViewModelProviders.of(this).get(VideoListViewModel::class.java)

        swipeRefresh.setOnRefreshListener(this)

        rv_videos.setHasFixedSize(true)
        adapter = VideosAdapter(
            ctx,
            videosList,
            object : OnItemClickListener {
                override fun itemClick(position: Int) {
                    //videoPresenter.videoItemClick(adapter?.getItem(position))
                }

                override fun itemLongClick(position: Int) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
        val layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
        rv_videos.layoutManager = layoutManager
        rv_videos.adapter = adapter

        rv_videos.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun loadMoreItems() {
            }
        })
    }

    private fun populateAdapterRight(videos: List<VideoUI>){
        adapter?.addItems(videos)
        swipeRefresh?.isRefreshing = false

    }

    override fun populateAdapter(videos: YoutubeModel) {
        /*if (currentPage != 1) {
            adapter?.removeLoading()
        }
        adapter?.addItems(videos.items)
        swipeRefresh?.isRefreshing = false*/
    }

    override fun showHideLoadingInAdapter(isShow: Boolean) {
        if (isShow) adapter?.addLoading()
        else adapter?.removeLoading()
    }

    override fun isLoadingNextPages(loading: Boolean) {
        isLoading = loading
    }

    override fun isLastLoadedPage(last: Boolean) {
        isLastPage = last
    }

    override fun incrementCurrentPageBeforeLoading() {
        currentPage++
    }

    override fun resetCurrentPage() {
        currentPage = 1
    }

    override fun clearRecyclerItems() {
        adapter?.clear()
    }

    override fun openVideoDetails(video: YoutubeModel) {
        /*val intent = Intent(ctx, VideoDetailActivity::class.java)
        intent.putExtra(Constants.INTENT_PARAM_YOUTUBE_MODEL, video)
        startActivity(intent)*/
    }

    override fun showErrorToast(message: String?) {
        rv_videos?.showSnackBar("Ошибка - $message")
    }

    override fun showHideLoadingView(isLoading: Boolean) {
        if(isLoading) ll_progress?.visibility = View.VISIBLE
        else ll_progress?.visibility = View.GONE

    }

    override fun onRefresh() {
    }
}
