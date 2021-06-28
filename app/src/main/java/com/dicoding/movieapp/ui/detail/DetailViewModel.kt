package com.dicoding.movieapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dicoding.movieapp.data.source.MovieAppRepository
import com.dicoding.movieapp.data.source.local.entity.MovieEntity
import com.dicoding.movieapp.data.source.local.entity.SeriesEntity
import com.dicoding.movieapp.vo.Resource

class DetailViewModel(private val movieAppRepository: MovieAppRepository): ViewModel() {

    val movieId = MutableLiveData<String>()

    fun setSelectedItem(movieId: String){
        this.movieId.value = movieId
    }

    var movieWithId: LiveData<Resource<MovieEntity>> = Transformations.switchMap(movieId){ mMovieId ->
        movieAppRepository.getMovieById(mMovieId)
    }

    var serieWithId: LiveData<Resource<SeriesEntity>> = Transformations.switchMap(movieId){ mSerieId ->
        movieAppRepository.getSeriesById(mSerieId)
    }

    fun setFavoriteMovie(){
        val resource = movieWithId.value
        if (resource != null){
            val newState = !resource.data?.favorite!!
            movieAppRepository.setFavoriteMovie(resource.data!!, newState)
        }
    }

    fun setFavoriteSerie(){
        val resource = serieWithId.value
        if (resource?.data != null){
            val newState = !resource.data!!.favorite
            movieAppRepository.setFavoriteSerie(resource.data!!, newState)
        }
    }


//    fun getMovie(): LiveData<MovieEntity> = movieAppRepository.getMovieById(movieId)
//
//    fun getTvSeries(): LiveData<SeriesEntity> = movieAppRepository.getSeriesById(movieId)
}