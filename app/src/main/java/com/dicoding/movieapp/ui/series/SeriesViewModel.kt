package com.dicoding.movieapp.ui.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.movieapp.data.source.MovieAppRepository
import com.dicoding.movieapp.data.source.local.entity.SeriesEntity
import com.dicoding.movieapp.vo.Resource

class SeriesViewModel(private val moveAppRepository: MovieAppRepository) : ViewModel() {

    fun getSeries(): LiveData<Resource<PagedList<SeriesEntity>>> = moveAppRepository.getAllSeries()
}