package sidev.app.course.dicoding.moviecatalog1.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import sidev.app.course.dicoding.moviecatalog1.UnitTestingUtil.waitForValue
import sidev.app.course.dicoding.moviecatalog1.repository.ShowRepo
import sidev.app.course.dicoding.moviecatalog1.util.Const
import sidev.app.course.dicoding.moviecatalog1.util.AppConfig
import sidev.lib.console.prin
import sidev.lib.console.prine

class ShowDetailViewModelTest {

    private lateinit var vm: ShowDetailViewModel
    private val repo: ShowRepo = AppConfig.defaultShowRepo

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        vm = ShowDetailViewModel(null, repo, Const.ShowType.MOVIE)
    }

    @Test
    fun downloadShowDetail(){
        vm.onCallNotSuccess { code, e ->
            prine("onCallNotSuccess code= $code e= $e")
        }
        vm.downloadShowDetail(AppConfig.dummyShowItem.id)

        val data = vm.showDetail.waitForValue()
        assertNotNull(data)
        assert(data.show.title.isNotBlank())
        assert(data.show.id.isNotBlank())
        assert(data.show.release.isNotBlank())
        assert(data.overview.isNotBlank())
        prin(data)
    }
}