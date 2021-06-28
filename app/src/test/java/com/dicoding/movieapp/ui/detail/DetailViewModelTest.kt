package com.dicoding.movieapp.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.movieapp.data.source.local.entity.MovieEntity
import com.dicoding.movieapp.data.source.local.entity.SeriesEntity
import com.dicoding.movieapp.data.source.MovieAppRepository
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
import org.mockito.stubbing.OngoingStubbing

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel
    private val dummyMovie = DataDummy.generateDummyMoviews()[0]
    private val dummySeries = DataDummy.generateDummySeries()[0]

    private val movieId = dummyMovie.id
    private val seriesId = dummySeries.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieAppRepository: MovieAppRepository



    @Mock
    private lateinit var movieObserver: Observer<Resource<MovieEntity>>

    @Mock
    private lateinit var seriesObserver: Observer<Resource<SeriesEntity>>

    @Before
    fun setUp(){
        viewModel = DetailViewModel(movieAppRepository)
        viewModel.setSelectedItem(movieId)
        viewModel.setSelectedItem(seriesId)
    }

    @Test
    fun getMovie() {
        val movie = Resource.success(DataDummy.generateDummyMoviews()[0])
        val data = MutableLiveData<Resource<MovieEntity>>()
        data.value = movie
        `when`(movieAppRepository.getMovieById(movieId)).thenReturn(data)
        viewModel.movieWithId.observeForever(movieObserver)
        verify(movieObserver).onChanged(movie)
//        versi sebelumnya
//        viewModel.setSelectedItem(dummyMovie.id)
//        val movieEntity = viewModel.movieWithId.value as MovieEntity
//        verify(movieAppRepository).getMovieById(movieId)
//        assertNotNull(movieEntity)
//        assertEquals(dummyMovie.id, movieEntity.id)
//        assertEquals(dummyMovie.title, movieEntity.title)
//        assertEquals(dummyMovie.genre, movieEntity.genre)
//        assertEquals(dummyMovie.description, movieEntity.description)
//        assertEquals(dummyMovie.year, movieEntity.year)
//        assertEquals(dummyMovie.cast, movieEntity.cast)
//        assertEquals(dummyMovie.imagePath, movieEntity.imagePath)
//
//        viewModel.movieWithId.observeForever(movieObserver)
//        verify(movieObserver).onChanged(dummyMovie)
    }

    @Test
    fun getTvSeries() {
        val series = Resource.success(DataDummy.generateDummySeries()[0])
        val data = MutableLiveData<Resource<SeriesEntity>>()
        data.value = series
        `when`(movieAppRepository.getSeriesById(seriesId)).thenReturn(data)
        viewModel.serieWithId.observeForever(seriesObserver)
        verify(seriesObserver).onChanged(series)
//        versi sebelumnya
//        viewModel.setSelectedItem(dummyMovie.id)
//        val movieEntity = viewModel.serieWithId.value as SeriesEntity
//        verify(movieAppRepository).getSeriesById(seriesId)
//        assertNotNull(movieEntity)
//        assertEquals(dummySeries.id, movieEntity.id)
//        assertEquals(dummySeries.title, movieEntity.title)
//        assertEquals(dummySeries.genre, movieEntity.genre)
//        assertEquals(dummySeries.description, movieEntity.description)
//        assertEquals(dummySeries.year, movieEntity.year)
//        assertEquals(dummySeries.cast, movieEntity.cast)
//        assertEquals(dummySeries.imagePath, movieEntity.imagePath)
//
//        viewModel.serieWithId.observeForever(seriesObserver)
//        verify(seriesObserver).onChanged(dummySeries)
    }
}


