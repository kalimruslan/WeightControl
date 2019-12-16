package ru.ruslan.weighttracker.videos.list.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.*
import ru.ruslan.weighttracker.data.datasource.api.model.response.YoutubeModel
import ru.ruslan.weighttracker.core.datatype.Result
import ru.ruslan.weighttracker.core.datatype.ResultType
import ru.ruslan.weighttracker.data.datasource.api.VideoListNetworkDataSource
import ru.ruslan.weighttracker.data.datasource.api.retrofit.ApiFactory
import ru.ruslan.weighttracker.data.repository.VideoListRepositoryImpl
import ru.ruslan.weighttracker.util.Constants
import ru.ruslan.weighttracker.util.printLog
import ru.ruslan.weighttracker.videos.list.VideoContract
import ru.ruslan.weighttracker.videos.list.domain.model.VideosEntity
import ru.ruslan.weighttracker.videos.list.domain.usecase.GetVideoListUseCase
import ru.ruslan.weighttracker.videos.list.vm.mapper.VideosEntityToUiMapper
import ru.ruslan.weighttracker.videos.list.vm.model.VideoUI
import kotlin.coroutines.CoroutineContext

class VideoListViewModel(application: Application) : AndroidViewModel(application) {

    private val getVideoListUseCase: GetVideoListUseCase = GetVideoListUseCase(
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

    init {
        handleVideosLoad()
    }

    fun handleVideosLoad() {
        updateLoadingLiveData(true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = getVideoListUseCase.getVideosByPlaylist(Constants.VIDEO_PLAYLIST_1, "")
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
        val videosUI = VideosEntityToUiMapper.map(data?.items)

        if (videosUI.isEmpty()) {
            "VIEW_MODEL: videoUI empty".printLog("CleanArch", Log.DEBUG)
        } else {
            videosMutableLiveData.value = videosUI
        }

        updateLoadingLiveData(false)
    }

    private fun updateLoadingLiveData(isLoading: Boolean) {
        isLoadingMutableLiveData.value = isLoading
    }

    private fun isResultSuccess(resultType: ResultType?): Boolean {
        return resultType == ResultType.SUCCESS
    }

}