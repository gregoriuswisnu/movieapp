package com.dicoding.movieapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.movieapp.data.source.MovieAppRepository
import com.dicoding.movieapp.di.Injection
import com.dicoding.movieapp.ui.detail.DetailViewModel
import com.dicoding.movieapp.ui.favorite.favmovies.FavMovieViewModel
import com.dicoding.movieapp.ui.favorite.favseries.FavSerieViewModel
import com.dicoding.movieapp.ui.movie.MovieViewModel
import com.dicoding.movieapp.ui.series.SeriesViewModel

class ViewModelFactory private constructor(private val mMovieAppRepository: MovieAppRepository): ViewModelProvider.NewInstanceFactory(){

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(MovieViewModel::class.java) ->{
                MovieViewModel(mMovieAppRepository) as T
            }
            modelClass.isAssignableFrom(SeriesViewModel::class.java) ->{
                SeriesViewModel(mMovieAppRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) ->{
                DetailViewModel(mMovieAppRepository) as T
            }
            modelClass.isAssignableFrom(FavSerieViewModel::class.java) ->{
                FavSerieViewModel(mMovieAppRepository) as T
            }
            modelClass.isAssignableFrom(FavMovieViewModel::class.java) ->{
                FavMovieViewModel(mMovieAppRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}