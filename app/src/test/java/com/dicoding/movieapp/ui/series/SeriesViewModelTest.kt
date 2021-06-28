package com.dicoding.movieapp.ui.series

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.dicoding.movieapp.data.source.local.entity.SeriesEntity
import com.dicoding.movieapp.data.source.MovieAppRepository
import com.dicoding.movieapp.ui.favorite.favseries.FavSerieViewModel
import com.dicoding.movieapp.utils.DataDummy
import com.dicoding.movieapp.vo.Resource
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SeriesViewModelTest {
    private lateinit var viewModel: SeriesViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieAppRepository: MovieAppRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<SeriesEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<SeriesEntity>

    @Before
    fun setUp(){
        viewModel = SeriesViewModel(movieAppRepository)
    }

    @Test
    fun getSeries() {
        val dummySeries = Resource.success(pagedList)
        `when`(dummySeries.data?.size).thenReturn(10)
        val series = MutableLiveData<Resource<PagedList<SeriesEntity>>>()
        series.value = dummySeries

        `when`(movieAppRepository.getAllSeries()).thenReturn(series)
        val seriesEntities = viewModel.getSeries().value?.data
        verify(movieAppRepository).getAllSeries()
        assertNotNull(seriesEntities)
        assertEquals(10, seriesEntities?.size)

        viewModel.getSeries().observeForever(observer)
        verify(observer).onChanged(dummySeries)
    }
}