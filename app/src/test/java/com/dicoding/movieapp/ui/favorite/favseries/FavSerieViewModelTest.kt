package com.dicoding.movieapp.ui.favorite.favseries

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.dicoding.movieapp.data.source.MovieAppRepository
import com.dicoding.movieapp.data.source.local.entity.MovieEntity
import com.dicoding.movieapp.data.source.local.entity.SeriesEntity
import com.dicoding.movieapp.ui.favorite.favmovies.FavMovieViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavSerieViewModelTest{
    private lateinit var viewModel: FavSerieViewModel

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieAppRepository: MovieAppRepository

    @Mock
    private lateinit var observer: Observer<PagedList<SeriesEntity>>

    @Mock
    private lateinit var pagedList: PagedList<SeriesEntity>

    @Before
    fun setUp(){
        viewModel = FavSerieViewModel(movieAppRepository)
    }

    @Test
    fun testGetMovie(){
        val dummySeries = pagedList
        `when`(dummySeries.size).thenReturn(10)
        val series = MutableLiveData<PagedList<SeriesEntity>>()
        series.value = dummySeries

        `when`(movieAppRepository.getFavoritedSeries()).thenReturn(series)
        val movieEntities = viewModel.getFavSeries().value
        verify(movieAppRepository).getFavoritedSeries()
        assertNotNull(movieEntities)
        assertEquals(10,movieEntities?.size)

        viewModel.getFavSeries().observeForever(observer)
        verify(observer).onChanged(dummySeries)
    }
}