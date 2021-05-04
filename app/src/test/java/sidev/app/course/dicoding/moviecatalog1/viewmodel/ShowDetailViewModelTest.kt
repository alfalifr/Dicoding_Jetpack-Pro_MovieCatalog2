package sidev.app.course.dicoding.moviecatalog1.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify
import sidev.app.course.dicoding.moviecatalog1.UnitTestingUtil.waitForValue
import sidev.app.course.dicoding.moviecatalog1.repository.ShowRepo
import sidev.app.course.dicoding.moviecatalog1.repository.Success
import sidev.app.course.dicoding.moviecatalog1.util.Const
import sidev.app.course.dicoding.moviecatalog1.util.AppConfig

class ShowDetailViewModelTest {

    companion object {
        private val repo: ShowRepo by lazy { Mockito.mock(ShowRepo::class.java) }

        private val movieDetail = AppConfig.dummyMovieDetail
        private val tvDetail = AppConfig.dummyTvDetail

        @BeforeClass
        @JvmStatic
        fun initSetup(): Unit = runBlocking {
            Mockito.`when`(repo.getMovieDetail(Mockito.any(), Mockito.anyString())).thenReturn(
                Success(movieDetail)
            )
            Mockito.`when`(repo.getTvDetail(Mockito.any(), Mockito.anyString())).thenReturn(
                Success(tvDetail)
            )
        }
    }

    private lateinit var vm: ShowDetailViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun downloadMovieDetail(): Unit = runBlocking {
        val data = AppConfig.dummyMovieItem
        vm = ShowDetailViewModel(null, repo, Const.ShowType.MOVIE)
        vm.downloadShowDetail(data.id)

        val dataFromCall = vm.showDetail.waitForValue()

        verify(repo).getMovieDetail(null, data.id)
        assertEquals(movieDetail, dataFromCall)
    }

    @Test
    fun downloadTvDetail(): Unit = runBlocking {
        val data = AppConfig.dummyTvItem
        vm = ShowDetailViewModel(null, repo, Const.ShowType.TV)
        vm.downloadShowDetail(data.id)

        val dataFromCall = vm.showDetail.waitForValue()

        verify(repo).getTvDetail(null, data.id)
        assertEquals(tvDetail, dataFromCall)
    }
}