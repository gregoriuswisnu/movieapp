package com.dicoding.movieapp.ui.favorite.favseries

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.movieapp.data.source.MovieAppRepository
import com.dicoding.movieapp.data.source.local.entity.SeriesEntity

class FavSerieViewModel(private val movieAppRepository: MovieAppRepository): ViewModel() {
    fun getFavSeries(): LiveData<PagedList<SeriesEntity>> = movieAppRepository.getFavoritedSeries()

    fun setFavSeries(seriesEntity: SeriesEntity){
        val newState = !seriesEntity.favorite
        movieAppRepository.setFavoriteSerie(seriesEntity, newState)
    }
}