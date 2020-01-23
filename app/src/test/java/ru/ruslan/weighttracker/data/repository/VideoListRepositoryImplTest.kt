package ru.ruslan.weighttracker.data.repository

import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import ru.ruslan.weightracker.core.datatype.Result

import ru.ruslan.weighttracker.data.datasource.api.VideoListNetworkDataSource
import ru.ruslan.weighttracker.data.datasource.api.exceptions.NoConnectivityException
import ru.ruslan.weighttracker.data.repository.mapper.ApiToEntityMapper
import ru.ruslan.weighttracker.fake.FakeDataLayerFactory
import ru.ruslan.weighttracker.fake.FakeDomainLayerFactory

class VideoListRepositoryImplTest {
    @MockK
    private lateinit var videoListNetworkDataSource: VideoListNetworkDataSource
    @MockK
    private lateinit var apiToEntityMapper: ApiToEntityMapper

    private lateinit var repositoryImpl: VideoListRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this, true)
        repositoryImpl = VideoListRepositoryImpl(videoListNetworkDataSource)
    }

    @Test
    fun `should return success Result of Videos Entity`() {
        // arrange
        val expectedYoutubeResponse = FakeDataLayerFactory.makeYoutubeResponse(3)
        val expectedVideosEntity = FakeDomainLayerFactory.makeVideosEntity(3)
        val resultSuccessVideosEntity = Result.success(expectedVideosEntity)
        coEvery { videoListNetworkDataSource.getVideosForPlayList("", "") } returns
                Result.success(expectedYoutubeResponse)
        every { apiToEntityMapper.map(any()) } returns expectedVideosEntity

        //act
        val result = runBlocking { repositoryImpl.getVideosForPlayList("", "")}

        //assert
        assertEquals(resultSuccessVideosEntity, result)
    }

    @Test
    fun `should return error Result of Videos Entity`(){
        // arrange
        val resultErrorVideosEntity = Result.error<NoConnectivityException>(NoConnectivityException(""))
        coEvery { videoListNetworkDataSource.getVideosForPlayList("", "") } returns
                Result.error(NoConnectivityException(""))

        //act
        val result = runBlocking { repositoryImpl.getVideosForPlayList("", "")}

        //assert
        assertEquals(resultErrorVideosEntity, result)
    }

}