package com.dicoding.movieapp.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dicoding.movieapp.data.source.local.entity.MovieEntity
import com.dicoding.movieapp.data.source.local.entity.SeriesEntity
import com.dicoding.movieapp.vo.Resource

interface MovieAppDataSource {

    fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>>

    fun getAllSeries(): LiveData<Resource<PagedList<SeriesEntity>>>

    fun getMovieById(movieId: String): LiveData<Resource<MovieEntity>>

    fun getSeriesById(seriesId: String) : LiveData<Resource<SeriesEntity>>

    fun getFavoritedMovie(): LiveData<PagedList<MovieEntity>>

    fun setFavoriteMovie(movie: MovieEntity, state: Boolean)

    fun getFavoritedSeries(): LiveData<PagedList<SeriesEntity>>

    fun setFavoriteSerie(series: SeriesEntity, state: Boolean)
}