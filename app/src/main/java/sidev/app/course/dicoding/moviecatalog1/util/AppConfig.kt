package sidev.app.course.dicoding.moviecatalog1.util

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource
import sidev.app.course.dicoding.moviecatalog1.datasource.ShowDataSource
import sidev.app.course.dicoding.moviecatalog1.datasource.ShowRemoteRetrofitSource
import sidev.app.course.dicoding.moviecatalog1.datasource.ShowRemoteSource
import sidev.app.course.dicoding.moviecatalog1.model.Show
import sidev.app.course.dicoding.moviecatalog1.model.ShowDetail
import sidev.app.course.dicoding.moviecatalog1.repository.ShowApiRepo
import sidev.app.course.dicoding.moviecatalog1.repository.ShowRepo

/**
 * Value inside this class should be modified in testing process, e.g. unit / instrumented testing.
 */
object AppConfig {
    enum class UiTestType {
        ESPRESSO, ROBOLECTRIC
    }

    private val mIdlingRes by lazy { CountingIdlingResource("GLOBAL") }
    private val mLock by lazy { CountingLatch() }

    private var uiTestType = UiTestType.ESPRESSO

    var defaultShowRemoteSource: ShowDataSource = ShowRemoteSource
    var defaultShowRepo: ShowRepo = ShowApiRepo(defaultShowRemoteSource)

    fun resetDefautlShowRepo(){
        defaultShowRepo = ShowApiRepo(defaultShowRemoteSource)
    }
    fun resetDefautlShowRemoteSource(){
        defaultShowRemoteSource = ShowRemoteSource
    }

    var isUiAsyncTest = false

    val idlingRes: IdlingResource?
        get()= if(isUiAsyncTest) mIdlingRes else null

    fun incUiAsync(){
        if(isUiAsyncTest) when(uiTestType){
            UiTestType.ESPRESSO -> mIdlingRes.increment()
            UiTestType.ROBOLECTRIC -> mLock.increment()
        }
    }
    fun decUiAsync(){
        if(isUiAsyncTest) when(uiTestType){
            UiTestType.ESPRESSO -> mIdlingRes.decrement()
            UiTestType.ROBOLECTRIC -> mLock.decrement()
        }
    }


    val dummyShowType = Const.ShowType.MOVIE
    val dummyShowItem = Show(
        id="458576",
        title="Monster Hunter",
        img="/1UCOF11QCw8kcqvce8LKOO6pimh.jpg",
        release="2020-12-03",
        rating=7.1
    )
    val dummyShowDetail = ShowDetail(
        dummyShowItem,
        listOf("Cooking", "Action"),
        145,
        "He keeps moving forward until all his enemies get destroyed",
        "When Erwin has his 'SASAGEYO', Eren has his 'TATAKAE'",
        "/yvKrycViRMQcIgdnjsM5JGNWU4Q.jpg"
    )
}