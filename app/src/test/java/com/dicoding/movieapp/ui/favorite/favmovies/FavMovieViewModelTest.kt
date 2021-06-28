package com.dicoding.movieapp.ui.favorite.favmovies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.dicoding.movieapp.data.source.MovieAppRepository
import com.dicoding.movieapp.data.source.local.entity.MovieEntity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavMovieViewModelTest{
    private lateinit var viewModel: FavMovieViewModel

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieAppRepository: MovieAppRepository

    @Mock
    private lateinit var observer: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @Before
    fun setUp(){
        viewModel = FavMovieViewModel(movieAppRepository)
    }

    @Test
    fun testGetMovie(){
        val dummyMovie = pagedList
        `when`(dummyMovie.size).thenReturn(10)
        val movie = MutableLiveData<PagedList<MovieEntity>>()
        movie.value = dummyMovie

        `when`(movieAppRepository.getFavoritedMovie()).thenReturn(movie)
        val movieEntities = viewModel.getFavMovies().value
        Mockito.verify(movieAppRepository).getFavoritedMovie()
        assertNotNull(movieEntities)
        assertEquals(10,movieEntities?.size)

        viewModel.getFavMovies().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyMovie)
    }
}