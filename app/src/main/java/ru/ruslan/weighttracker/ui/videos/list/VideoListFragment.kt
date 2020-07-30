package ru.ruslan.weighttracker.ui.videos.list

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_videos.*
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.dagger.scope.VideoListScope
import ru.ruslan.weighttracker.domain.contract.VideoListContract
import ru.ruslan.weighttracker.ui.BaseFragment
import ru.ruslan.weighttracker.ui.OnItemClickListener
import ru.ruslan.weighttracker.ui.PaginationScrollListener
import ru.ruslan.weighttracker.ui.home.HomeFragment
import ru.ruslan.weighttracker.ui.util.Constants
import ru.ruslan.weighttracker.ui.util.Constants.THIS_APP
import ru.ruslan.weighttracker.ui.util.showToast
import ru.ruslan.weighttracker.ui.videos.detail.VideoDetailActivity
import ru.ruslan.weighttracker.ui.videos.list.model.VideoUI
import javax.inject.Inject

@VideoListScope
class VideoListFragment: BaseFragment(R.layout.fragment_videos), VideoListContract.View, SwipeRefreshLayout.OnRefreshListener {

    private var adapter: VideosAdapter? = null
    private var videosList: MutableList<VideoUI> = mutableListOf()
    private var isLastPage = false
    private var isLoading = false
    private lateinit var layoutManager: LinearLayoutManager
    @Inject lateinit var presenter: VideoListContract.Presenter

    companion object {
        @JvmStatic
        fun newInstance() = VideoListFragment()
    }

    override fun initDagger() {
        THIS_APP.getAppComponent().getVideoListComponent().create().inject(this)
    }

    override fun initVars() {
        presenter.setView(this)
        presenter.initPresenter()
    }

    override fun initViews() {
        rv_videos.setHasFixedSize(true)
        adapter = VideosAdapter(
            context,
            videosList,
            object : OnItemClickListener {
                override fun itemClick(position: Int) {
                    val intent = Intent(context, VideoDetailActivity::class.java)
                    intent.putExtra(Constants.EXTRA_PARAM_VIDEO, videosList[position])
                    startActivity(intent)
                }

                override fun itemLongClick(position: Int) {
                }
            })
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_videos.layoutManager = layoutManager
        rv_videos.adapter = adapter
    }

    override fun setListeners() {
        swipeRefresh.setOnRefreshListener(this)

        rv_videos.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun loadMoreItems() {
                presenter.downloadNextPages()
            }
        })
    }

    override fun populateAdapter(videos: List<VideoUI>) {
        if (presenter.getCurrentPage() != 1) {
            adapter?.removeLoading()
        }
        videosList.addAll(videos)
        adapter?.addItems(videosList)
        swipeRefresh?.isRefreshing = false
        isLoading = false
    }

    override fun showHideLoadingView(isLoading: Boolean) {
        if(isLoading) ll_progress?.visibility = View.VISIBLE
        else ll_progress?.visibility = View.GONE
    }

    override fun isLoadingNextPages(isLoading: Boolean) {
        this.isLoading = isLoading
    }

    override fun showHideLoadingInAdapter(isShow: Boolean) {
        if (isShow) adapter?.addLoading()
        else adapter?.removeLoading()
    }

    override fun isLastLoadedPage(last: Boolean) {
        isLastPage = last
    }

    override fun errorLoading(error: String) {
        context?.let { error.showToast(it) }
    }

    override fun onRefresh() {
        presenter.refreshViews()
        adapter?.clear()
    }
}