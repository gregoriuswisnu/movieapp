package com.dicoding.movieapp.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.dicoding.movieapp.data.source.local.entity.MovieEntity
import com.dicoding.movieapp.data.source.local.entity.SeriesEntity

@Dao
interface MovieAppDao {

    @Query("SELECT * FROM movieentities")
    fun getMovie(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movieentities where favorite = 1")
    fun getFavoriteMovie(): DataSource.Factory<Int, MovieEntity>

    @Transaction
    @Query("SELECT * FROM movieentities where movieId = :movieId")
    fun getMovieById(movieId: String): LiveData<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movies: List<MovieEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)

    @Query("SELECT * FROM seriesentities")
    fun getSeries(): DataSource.Factory<Int, SeriesEntity>

    @Query("select * from seriesentities where favorite = 1")
    fun getFavoriteSerie(): DataSource.Factory<Int, SeriesEntity>

    @Transaction
    @Query("select * from seriesentities where seriesId =:serieId")
    fun getSeriesById(serieId: String): LiveData<SeriesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSerie(series: List<SeriesEntity>)

    @Update
    fun updateSerie(serie: SeriesEntity)

}