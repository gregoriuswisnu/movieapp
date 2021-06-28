package com.dicoding.movieapp.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.movieapp.data.source.MovieAppRepository
import com.dicoding.movieapp.data.source.local.entity.MovieEntity
import com.dicoding.movieapp.vo.Resource

class MovieViewModel(private val movieAppRepository: MovieAppRepository): ViewModel() {

    fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>> = movieAppRepository.getAllMovies()
}