package com.dicoding.movieapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dicoding.movieapp.data.source.local.entity.MovieEntity
import com.dicoding.movieapp.data.source.local.entity.SeriesEntity
import com.dicoding.movieapp.data.source.MovieAppDataSource
import com.dicoding.movieapp.data.source.local.LocalDataSource
import com.dicoding.movieapp.data.source.remote.ApiResponse
import com.dicoding.movieapp.data.source.remote.NetworkBoundResource
import com.dicoding.movieapp.data.source.remote.RemoteDataSource
import com.dicoding.movieapp.data.source.remote.response.MovieResponse
import com.dicoding.movieapp.data.source.remote.response.SeriesResponse
import com.dicoding.movieapp.utils.AppExecutors
import com.dicoding.movieapp.vo.Resource

class FakeMovieAppRepository (
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource,
        private val appExecutors: AppExecutors)
    : MovieAppDataSource {


    override fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<MovieResponse>>(appExecutors){

            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(4)
                        .setPageSize(4)
                        .build()
                return LivePagedListBuilder(localDataSource.getAllMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> {
                return remoteDataSource.getAllMovies()
            }

            override fun saveCallResult(data: List<MovieResponse>) {
                val movieList = ArrayList<MovieEntity>()
                for (i in data.indices){
                        val response = data[i]
                        val movie = MovieEntity(
                                response.id,
                                response.title,
                                response.genre,
                                response.description,
                                response.year,
                                response.cast,
                                false,
                                response.imagePath)
                        movieList.add(movie)
                    }

                    localDataSource.insertMovies(movieList)
                }
        }.asLiveData()
    }

    override fun getFavoritedMovie(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(4)
                .setPageSize(4)
                .build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovies(), config).build()
    }

    override fun setFavoriteMovie(movie: MovieEntity, state: Boolean) {
        return appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movie, state) }
    }

    override fun getFavoritedSeries(): LiveData<PagedList<SeriesEntity>> {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(4)
                .setPageSize(4)
                .build()
        return LivePagedListBuilder(localDataSource.getFavoriteSeries(), config).build()
    }

    override fun setFavoriteSerie(series: SeriesEntity, state: Boolean) {
        return appExecutors.diskIO().execute { localDataSource.setFavoriteSeries(series, state) }
    }

    override fun getAllSeries(): LiveData<Resource<PagedList<SeriesEntity>>>{
        return object : NetworkBoundResource<PagedList<SeriesEntity>, List<SeriesResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<SeriesEntity>> {
                val config = PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(4)
                        .setPageSize(4)
                        .build()
                return LivePagedListBuilder(localDataSource.getAllseries(), config).build()
            }

            override fun shouldFetch(data: PagedList<SeriesEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<SeriesResponse>>> {
                return remoteDataSource.getAllSeries()
            }

            override fun saveCallResult(data: List<SeriesResponse>) {
                val serieList = ArrayList<SeriesEntity>()
                for (i in data.indices){
                    val response = data[i]
                    val serie = SeriesEntity(
                            response.id,
                            response.title,
                            response.description,
                            response.year,
                            response.imagePath,
                            response.genre,
                            response.cast,
                            false)
                    serieList.add(serie)
                }
                localDataSource.insertSeries(serieList)
            }
        }.asLiveData()
    }

    override fun getMovieById(movieId: String): LiveData<Resource<MovieEntity>>{
        return object : NetworkBoundResource<MovieEntity, List<MovieResponse>>(appExecutors){
            override fun loadFromDB(): LiveData<MovieEntity> {
                return localDataSource.getMovieById(movieId)
            }

            override fun shouldFetch(data: MovieEntity?): Boolean {
                return data?.id == null || data.id.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> {
                return remoteDataSource.getAllMovies()
            }

            override fun saveCallResult(data: List<MovieResponse>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in data){
                    val movie = MovieEntity(response.id,
                            response.title,
                            response.genre,
                            response.description,
                            response.year,
                            response.cast,
                            false,
                            response.imagePath)
                    movieList.add(movie)
                }
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getSeriesById(seriesId: String): LiveData<Resource<SeriesEntity>>{
        return object : NetworkBoundResource<SeriesEntity, List<SeriesResponse>>(appExecutors){
            override fun loadFromDB(): LiveData<SeriesEntity> {
                return localDataSource.getSeriesById(seriesId)
            }

            override fun shouldFetch(data: SeriesEntity?): Boolean {
                return data?.id == null || data.id.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<SeriesResponse>>> {
                return remoteDataSource.getAllSeries()
            }

            override fun saveCallResult(data: List<SeriesResponse>) {
                val seriesList = ArrayList<SeriesEntity>()
                for (response in data){
                    val series = SeriesEntity(
                            response.id,
                            response.title,
                            response.description,
                            response.year,
                            response.imagePath,
                            response.genre,
                            response.cast,
                            false
                    )
                    seriesList.add(series)
                }
                localDataSource.insertSeries(seriesList)
            }
        }.asLiveData()
    }
}