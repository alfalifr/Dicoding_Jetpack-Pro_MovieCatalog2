package sidev.app.course.dicoding.moviecatalog1.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify
import sidev.app.course.dicoding.moviecatalog1.UnitTestingUtil.waitForValue
import sidev.app.course.dicoding.moviecatalog1.data.repository.ShowRepo
import sidev.app.course.dicoding.moviecatalog1.data.Success
import sidev.app.course.dicoding.moviecatalog1.util.AppConfig
import sidev.app.course.dicoding.moviecatalog1.util.Const

class ShowListViewModelTest {

    companion object {
        // These props are lazy val so there's no way the value will be changed after initialization.
        private val repo: ShowRepo by lazy { Mockito.mock(ShowRepo::class.java) }

        private val movie = AppConfig.dummyMovieItem
        private val tv = AppConfig.dummyTvItem

        @BeforeClass
        @JvmStatic
        fun initSetup(): Unit = runBlocking {
            Mockito.`when`(repo.getPopularMovieList(Mockito.any())).thenReturn(
                Success(listOf(movie))
            )
            Mockito.`when`(repo.getPopularTvList(Mockito.any())).thenReturn(
                Success(listOf(tv))
            )
        }
    }

    private lateinit var vm: ShowListViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun downloadPopularMovieList(): Unit = runBlocking {
        vm = ShowListViewModel(null, repo, Const.ShowType.MOVIE)
        vm.downloadShowPopularList()

        val list = vm.showList.waitForValue()

        verify(repo).getPopularMovieList(null)
        assertNotNull(list)
        assert(list.isNotEmpty())

        val dataFromList = list.first()
        assertEquals(movie, dataFromList)
    }

    @Test
    fun downloadPopularTvList(): Unit = runBlocking {
        vm = ShowListViewModel(null, repo, Const.ShowType.TV)
        vm.downloadShowPopularList()

        val list = vm.showList.waitForValue()

        verify(repo).getPopularTvList(null)
        assertNotNull(list)
        assert(list.isNotEmpty())

        val dataFromList = list.first()
        assertEquals(tv, dataFromList)
    }
}