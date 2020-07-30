package ru.ruslan.weighttracker.domain

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.any
import ru.ruslan.weightracker.core.datatype.Result
import ru.ruslan.weighttracker.data.datasource.api.exceptions.NoConnectivityException
import ru.ruslan.weighttracker.data.repository.VideoListRepositoryImpl
import ru.ruslan.weighttracker.domain.repository.VideoListRepository
import ru.ruslan.weighttracker.domain.usecase.GetVideoListUseCase
import ru.ruslan.weighttracker.fake.FakeDomainLayerFactory
import java.lang.Exception

class GetVideoListUseCaseTest {

    @MockK
    private lateinit var remoteRepo: VideoListRepository
    @MockK
    private lateinit var exception: Exception
    @MockK
    private lateinit var callback: GetVideoListUseCase.Callback

    private lateinit var getVideoListUseCase: GetVideoListUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, true)
        getVideoListUseCase = GetVideoListUseCase(remoteRepo)
    }

    @Test
    fun `should return success Result of Videos Entity`() {
        val expectedVideosEntity = FakeDomainLayerFactory.makeVideosEntity(10)
        val expectedResultSuccess = Result.success(expectedVideosEntity)
        coEvery { remoteRepo.getVideosForPlayList("", "") } returns expectedResultSuccess

        val result = runBlocking { getVideoListUseCase.getVideosByPlaylist("", "", callback) }

        assertEquals(expectedResultSuccess, result)
    }

    @Test
    fun `should return error Result of Videos Entity`(){
        val expectedResultError = Result.error<Exception>(exception)
        coEvery{remoteRepo.getVideosForPlayList("","")} returns Result.error(exception)

        val result = runBlocking { getVideoListUseCase.getVideosByPlaylist("", "", callback) }
        assertEquals(expectedResultError, result)
    }
}