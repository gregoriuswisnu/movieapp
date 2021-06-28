package com.dicoding.movieapp.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dicoding.movieapp.data.FakeMovieAppRepository
import com.dicoding.movieapp.data.source.local.LocalDataSource
import com.dicoding.movieapp.data.source.local.entity.MovieEntity
import com.dicoding.movieapp.data.source.local.entity.SeriesEntity
import com.dicoding.movieapp.data.source.remote.RemoteDataSource
import com.dicoding.movieapp.data.source.remote.response.MovieResponse
import com.dicoding.movieapp.utils.AppExecutors
import com.dicoding.movieapp.utils.DataDummy
import com.dicoding.movieapp.utils.LiveDataTestUtil
import com.dicoding.movieapp.utils.PagedListUtil
import com.dicoding.movieapp.vo.Resource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MovieAppRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val movieAppRepository = FakeMovieAppRepository(remote, local, appExecutors)

    private val movieResponses = DataDummy.generateRemoteDummyMoviews()
    private val movieId = movieResponses[0].id
    private val seriesResponse = DataDummy.generateRemoteDummySeries()
    private val seriesId = seriesResponse[0].id



    @Test
    fun getAllMovies() {
        val dataSource = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllMovies()).thenReturn(dataSource)
        movieAppRepository.getAllMovies()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMoviews()))
        verify(local).getAllMovies()
        assertNotNull(movieEntities.data)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())

    }

    @Test
    fun getAllSeries() {
        val dataSource= mock(DataSource.Factory::class.java) as DataSource.Factory<Int, SeriesEntity>
        `when`(local.getAllseries()).thenReturn(dataSource)
        movieAppRepository.getAllSeries()

        val seriesEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummySeries()))
        verify(local).getAllseries()
        assertNotNull(seriesEntities.data)
        assertEquals(seriesResponse.size.toLong(), seriesEntities.data?.size?.toLong())
    }

    @Test
    fun getMovieById() {
        val dummyEntity = MutableLiveData<MovieEntity>()
        dummyEntity.value = DataDummy.generateDummyMoviews()[0]
        `when`(local.getMovieById(movieId)).thenReturn(dummyEntity)

        val movieEntities = LiveDataTestUtil.getValue(movieAppRepository.getMovieById(movieId))
        verify(local).getMovieById(movieId)
        assertNotNull(movieEntities)
        assertNotNull(movieEntities.data)
        assertEquals(movieResponses[0].title, movieEntities.data?.title)
    }

    @Test
    fun getSeriesById() {
        val dummyEntity = MutableLiveData<SeriesEntity>()
        dummyEntity.value = DataDummy.generateDummySeries()[0]
        `when`(local.getSeriesById(seriesId)).thenReturn(dummyEntity)

        val seriesEntities = LiveDataTestUtil.getValue(movieAppRepository.getSeriesById(seriesId))
        verify(local).getSeriesById(seriesId)
        assertNotNull(seriesEntities)
        assertNotNull(seriesEntities.data)
        assertEquals(seriesResponse[0].title, seriesEntities.data?.title)
    }

    @Test
    fun getFavoriteMovie(){
        val dataSourceFactory = mock(androidx.paging.DataSource.Factory::class.java) as androidx.paging.DataSource.Factory<Int, MovieEntity>
        `when`(local.getFavoriteMovies()).thenReturn(dataSourceFactory)
        movieAppRepository.getFavoritedMovie()
        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMoviews()))
        verify(local).getFavoriteMovies()
        assertNotNull(movieEntities)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getFavoriteSeries(){
        val dataSourceFactory = mock(androidx.paging.DataSource.Factory::class.java) as androidx.paging.DataSource.Factory<Int, SeriesEntity>
        `when`(local.getFavoriteSeries()).thenReturn(dataSourceFactory)
        movieAppRepository.getFavoritedSeries()
        val seriesEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummySeries()))
        verify(local).getFavoriteSeries()
        assertNotNull(seriesEntities)
        assertEquals(movieResponses.size.toLong(), seriesEntities.data?.size?.toLong())
    }
}