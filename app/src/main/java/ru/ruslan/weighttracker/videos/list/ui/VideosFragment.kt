package ru.ruslan.weighttracker.videos.list.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_videos.*
import ru.ruslan.weighttracker.OnItemClickListener
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.util.Constants
import ru.ruslan.weighttracker.util.PaginationScrollListener
import ru.ruslan.weighttracker.util.showToast
import ru.ruslan.weighttracker.videos.detail.VideoDetailActivity
import ru.ruslan.weighttracker.videos.list.vm.VideoListViewModel
import ru.ruslan.weighttracker.videos.list.vm.model.VideoUI
import javax.inject.Inject

class VideosFragment : DaggerFragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var ctx: Context
    private var adapter: VideosAdapter? = null
    private var videosList: MutableList<VideoUI> = mutableListOf()
    private var isLastPage = false
    private var isLoading = false
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

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
        viewModel?.videosLiveData?.observe(this, Observer(::populateAdapter))
        viewModel?.isLoadingLiveData?.observe(this, Observer(::showHideLoadingView))
        viewModel?.isLoadingNextPagesLiveData?.observe(this, Observer(::isLoadingNextPages))
        viewModel?.showHideLoadingInadapterLiveData?.observe(this, Observer(::showHideLoadingInAdapter))
        viewModel?.isLastLoadPageLiveData?.observe(this, Observer(::isLastLoadedPage))
        viewModel?.erroForLoadingLiveData?.observe(this, Observer(::errorLoading))
    }

    private fun initVars() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(VideoListViewModel::class.java)

        swipeRefresh.setOnRefreshListener(this)

        rv_videos.setHasFixedSize(true)
        adapter = VideosAdapter(
            ctx,
            videosList,
            object : OnItemClickListener {
                override fun itemClick(position: Int) {
                    val intent = Intent(ctx, VideoDetailActivity::class.java)
                    intent.putExtra(Constants.EXTRA_PARAM_VIDEO, videosList[position])
                    startActivity(intent)
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
                viewModel?.needNextPages()
            }
        })
    }

    private fun populateAdapter(videos: List<VideoUI>){
        if (viewModel?.currentPage != 1) {
            adapter?.removeLoading()
        }
        videosList.addAll(videos)
        adapter?.addItems(videosList)
        swipeRefresh?.isRefreshing = false
        isLoading = false
    }

    private fun showHideLoadingInAdapter(isShow: Boolean) {
        if (isShow) adapter?.addLoading()
        else adapter?.removeLoading()
    }

    private fun isLoadingNextPages(loading: Boolean) {
        isLoading = loading
    }

    private fun isLastLoadedPage(last: Boolean) {
        isLastPage = last
    }

    private fun clearRecyclerItems() {
        adapter?.clear()
    }

    private fun showHideLoadingView(isLoading: Boolean) {
        if(isLoading) ll_progress?.visibility = View.VISIBLE
        else ll_progress?.visibility = View.GONE

    }

    private fun errorLoading(error: String){
        error.showToast(ctx)
    }

    override fun onRefresh() {
        viewModel?.currentPage = 1
        isLastLoadedPage(false)
        clearRecyclerItems()
        viewModel?.handleVideosLoad(Constants.VIDEO_PLAYLIST, "")
    }
}
