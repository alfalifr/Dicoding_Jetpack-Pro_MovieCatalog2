package sidev.app.course.dicoding.moviecatalog1

import org.junit.Test
import retrofit2.Retrofit
import sidev.app.course.dicoding.moviecatalog1.api.ShowApi
import sidev.app.course.dicoding.moviecatalog1.api.ShowConverterFactory
import sidev.app.course.dicoding.moviecatalog1.repository.Success
import sidev.app.course.dicoding.moviecatalog1.util.Const
import sidev.app.course.dicoding.moviecatalog1.util.Util.onResult
import sidev.lib.console.prine
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class CobTest {
    @Test
    fun retroTest(){
        val retrofit = Retrofit.Builder()
            .baseUrl(Const.ENDPOINT_ROOT + "/")
            .addConverterFactory(ShowConverterFactory)
            .build()

        val lock = CountDownLatch(1)
        val api = retrofit.create(ShowApi::class.java)

        api.getMovieDetail("399566").onResult {
            if(it is Success){
                val detail = it.data
                prine("detail= $detail")
            } else {
                prine("it= $it")
            }
            lock.countDown()
        }
        /*
        val list = api.getMovieDetail(399566).enqueue(object : Callback<ShowDetail> {
            /**
             * Invoked for a received HTTP response.
             *
             *
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call [Response.isSuccessful] to determine if the response indicates success.
             */
            override fun onResponse(call: Call<ShowDetail>, response: Response<ShowDetail>) {
                val detail = response.body()
                prine("detail= $detail")
                lock.countDown()
            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected exception
             * occurred creating the request or processing the response.
             */
            override fun onFailure(call: Call<ShowDetail>, t: Throwable) {
                throw t.also { lock.countDown() }
            }
        })
         */
        lock.await(5, TimeUnit.SECONDS)
    }
}
