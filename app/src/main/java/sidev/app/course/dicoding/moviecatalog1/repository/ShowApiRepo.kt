package sidev.app.course.dicoding.moviecatalog1.repository

import android.content.Context
import sidev.app.course.dicoding.moviecatalog1.datasource.ShowDataSource
import sidev.app.course.dicoding.moviecatalog1.model.Show
import sidev.app.course.dicoding.moviecatalog1.model.ShowDetail

class ShowApiRepo(private val remoteDataSource: ShowDataSource): ShowRepo {
    override suspend fun getPopularMovieList(c: Context?): Result<List<Show>> = remoteDataSource.getPopularMovieList(c)
    override suspend fun getPopularTvList(c: Context?): Result<List<Show>> = remoteDataSource.getPopularTvList(c)

    override suspend fun getMovieDetail(c: Context?, id: String): Result<ShowDetail> = remoteDataSource.getMovieDetail(c, id)
    override suspend fun getTvDetail(c: Context?, id: String): Result<ShowDetail> = remoteDataSource.getTvDetail(c, id)
}