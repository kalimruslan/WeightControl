package ru.ruslan.weighttracker.ui.videos.list.vm

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import ru.ruslan.weightracker.core.datatype.Result
import ru.ruslan.weighttracker.domain.usecase.GetVideoListUseCase
import ru.ruslan.weighttracker.fake.FakeDomainLayerFactory
import ru.ruslan.weighttracker.fake.FakePresentationLayerFactory

class VideoListViewModelTest {

    @MockK
    private lateinit var getVideoListUseCase: GetVideoListUseCase

    private lateinit var viewModel: VideoListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(true)
        viewModel = VideoListViewModel(getVideoListUseCase)
    }

    @Test
    fun `when success result for videos load, then videos live data set mapping ui model`() {
        val playlist = "testPlaylist"
        val pageToken = "testToken"
        val expectedVideosEntity = FakeDomainLayerFactory.makeVideosEntity(3)
        val expectedVideosUI = FakePresentationLayerFactory.makeVideoUI(3)

        coEvery{getVideoListUseCase.getVideosByPlaylist(playlist, pageToken)} returns
             Result.success(expectedVideosEntity)
    }

    @Test
    fun `when error result for videos load, then error live data set error message`() {

    }

    @Test
    fun `when has next page token and current page less that total page, then show hide loading view model set true `() {

    }

    @Test
    fun `when has next page token and current page more or equals that total page, then show hide loading view model set false `() {

    }

    @Test
    fun `when handle refresh view, then is last loaded view model set false`(){

    }

    @Test
    fun `when handle refresh view, then execute handle videos load`(){

    }
}