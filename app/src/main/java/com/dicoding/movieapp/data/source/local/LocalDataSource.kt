package com.dicoding.movieapp.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.dicoding.movieapp.data.source.local.entity.MovieEntity
import com.dicoding.movieapp.data.source.local.entity.SeriesEntity
import com.dicoding.movieapp.data.source.local.room.MovieAppDao

class LocalDataSource private constructor(private val mMovieAppDao: MovieAppDao){

    companion object{
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieAppDao: MovieAppDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieAppDao)
    }

    fun getAllMovies(): DataSource.Factory<Int, MovieEntity> = mMovieAppDao.getMovie()

    fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntity> = mMovieAppDao.getFavoriteMovie()

    fun getMovieById(movieId:String): LiveData<MovieEntity> = mMovieAppDao.getMovieById(movieId)

    fun insertMovies(movies: List<MovieEntity>) = mMovieAppDao.insertMovie(movies)

    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean){
        movie.favorite = newState
        mMovieAppDao.updateMovie(movie)
    }

    fun getAllseries(): DataSource.Factory<Int, SeriesEntity> = mMovieAppDao.getSeries()

    fun getFavoriteSeries(): DataSource.Factory<Int, SeriesEntity> = mMovieAppDao.getFavoriteSerie()

    fun getSeriesById(seriesId: String): LiveData<SeriesEntity> = mMovieAppDao.getSeriesById(seriesId)

    fun insertSeries(series: List<SeriesEntity>) = mMovieAppDao.insertSerie(series)

    fun setFavoriteSeries(series: SeriesEntity, newState: Boolean){
        series.favorite = newState
        mMovieAppDao.updateSerie(series)
    }
}