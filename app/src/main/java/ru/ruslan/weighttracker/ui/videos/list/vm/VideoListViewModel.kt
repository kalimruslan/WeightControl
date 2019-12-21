package ru.ruslan.weighttracker.ui.videos.list.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.*
import ru.ruslan.weighttracker.core.datatype.Result
import ru.ruslan.weighttracker.core.datatype.ResultType
import ru.ruslan.weighttracker.data.datasource.api.VideoListNetworkDataSource
import ru.ruslan.weighttracker.data.datasource.api.retrofit.ApiFactory
import ru.ruslan.weighttracker.data.repository.VideoListRepositoryImpl
import ru.ruslan.weighttracker.util.Constants
import ru.ruslan.weighttracker.util.printLog
import ru.ruslan.weighttracker.domain.model.videolists.VideosEntity
import ru.ruslan.weighttracker.domain.usecase.GetVideoListUseCase
import ru.ruslan.weighttracker.ui.videos.list.vm.mapper.VideosEntityToUiMapper
import ru.ruslan.weighttracker.ui.videos.list.vm.model.VideoUI

class VideoListViewModel(application: Application) : AndroidViewModel(application) {

    var currentPage = 1
    private var totalPage = 0
    private var nextPageToken: String = ""

    private val getVideoListUseCase: GetVideoListUseCase =
        GetVideoListUseCase(
            VideoListRepositoryImpl(
                VideoListNetworkDataSource(
                    ApiFactory.getRestClient(getApplication())
                )
            )
        )

    private val videosMutableLiveData: MutableLiveData<List<VideoUI>> = MutableLiveData()
    val videosLiveData: LiveData<List<VideoUI>>
        get() = videosMutableLiveData

    private val isLoadingMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isLoadingLiveData: LiveData<Boolean>
        get() = isLoadingMutableLiveData

    private val isLoadingNextPagesMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isLoadingNextPagesLiveData: LiveData<Boolean>
        get() = isLoadingNextPagesMutableLiveData

    private val showHideLoadingInAdapterMutableLiveData: MutableLiveData<Boolean> =
        MutableLiveData()
    val showHideLoadingInadapterLiveData: LiveData<Boolean>
        get() = showHideLoadingInAdapterMutableLiveData

    private val isLastLoadedPageMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isLastLoadPageLiveData: LiveData<Boolean>
        get() = isLastLoadedPageMutableLiveData

    init {
        handleVideosLoad(Constants.VIDEO_PLAYLIST, "")
    }

    fun handleVideosLoad(playlist: String, pageToken: String) {
        nextPageToken = pageToken
        if (pageToken.isEmpty())
            updateLoadingLiveData(true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = getVideoListUseCase.getVideosByPlaylist(playlist, pageToken)
            withContext(Dispatchers.Main) {
                updateLiveData(result)
            }
        }
    }

    private fun updateLiveData(result: Result<VideosEntity>?) {
        if (isResultSuccess(result?.resultType)) {
            onResultSuccess(result?.data)
        } else {
            onResultError()
        }
    }

    private fun onResultError() {
        updateLoadingLiveData(false)
    }

    private fun onResultSuccess(data: VideosEntity?) {
        nextPageToken = data?.nextPageToken!!
        totalPage = data.totalResult / data.resultPerPage

        val videosUI = VideosEntityToUiMapper.map(data.items)

        if (videosUI.isEmpty()) {
            "VIEW_MODEL: videoUI empty".printLog("CleanArch", Log.DEBUG)
        } else {
            videosMutableLiveData.value = videosUI
        }

        isLoadingNextPagesMutableLiveData.value = false
        updateLoadingLiveData(false)
    }

    private fun updateLoadingLiveData(isLoading: Boolean) {
        isLoadingMutableLiveData.value = isLoading
    }

    private fun isResultSuccess(resultType: ResultType?): Boolean {
        return resultType == ResultType.SUCCESS
    }

    fun needNextPages() {
        isLoadingNextPagesMutableLiveData.value = true
        currentPage++
        handleVideosLoad(Constants.VIDEO_PLAYLIST, nextPageToken)
        if (currentPage < totalPage) {
            showHideLoadingInAdapterMutableLiveData.value = true
        } else {
            isLastLoadedPageMutableLiveData.value = true
        }
    }

    fun handleRefreshViews() {
        currentPage = 1
        isLastLoadedPageMutableLiveData.value = false
        handleVideosLoad(Constants.VIDEO_PLAYLIST, "")
    }

}