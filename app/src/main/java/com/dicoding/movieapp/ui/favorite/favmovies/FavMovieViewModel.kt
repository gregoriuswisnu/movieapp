package com.dicoding.movieapp.ui.favorite.favmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.movieapp.data.source.MovieAppRepository
import com.dicoding.movieapp.data.source.local.entity.MovieEntity

class FavMovieViewModel(private val movieAppRepository: MovieAppRepository): ViewModel() {

    fun getFavMovies(): LiveData<PagedList<MovieEntity>> = movieAppRepository.getFavoritedMovie()

    fun setFavMovie(movieEntity: MovieEntity){
        val newState = !movieEntity.favorite
        movieAppRepository.setFavoriteMovie(movieEntity, newState)
    }
}