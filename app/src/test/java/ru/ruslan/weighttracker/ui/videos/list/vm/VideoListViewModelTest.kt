package ru.ruslan.weighttracker.ui.videos.list.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.ruslan.weightracker.core.datatype.Result
import ru.ruslan.weighttracker.domain.usecase.GetVideoListUseCase
import ru.ruslan.weighttracker.fake.FakeDomainLayerFactory
import ru.ruslan.weighttracker.fake.FakePresentationLayerFactory

class VideoListViewModelTest {

    @get:Rule var rule = InstantTaskExecutorRule()

    @MockK
    private lateinit var getVideoListUseCase: GetVideoListUseCase

    private lateinit var viewModel: VideoListViewModel

    @Before
    fun setUp(){
        MockKAnnotations.init(this, true)
        viewModel = VideoListViewModel(getVideoListUseCase)
    }

    @Test
    fun `handle videos load when page token is empty`(){
        val playList = FakePresentationLayerFactory.notEmptyString()
        val pageToken: String = FakePresentationLayerFactory.emptyString()

        viewModel.handleVideosLoad(playList, pageToken)

        verify { viewModel.updateLoadingLiveData(true) }
    }

    @Test
    fun `handle videos load when page token is not empty`(){
        val playList = FakePresentationLayerFactory.notEmptyString()
        val pageToken: String = FakePresentationLayerFactory.notEmptyString()
        val expectedVideosEntity = FakeDomainLayerFactory.makeVideosEntity(10)
        val expectedResultSuccess = Result.success(expectedVideosEntity)

        coEvery{getVideoListUseCase.getVideosByPlaylist(playList, pageToken)} returns expectedResultSuccess
        every { viewModel.updateLoadingLiveData(any()) } returns Unit

        runBlocking {  viewModel.handleVideosLoad(playList, pageToken) }

        verify{viewModel.updateLoadingLiveData(true)}

        //tect
    }

}