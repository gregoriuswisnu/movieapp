package com.dicoding.movieapp.di

import android.content.Context
import com.dicoding.movieapp.data.source.MovieAppRepository
import com.dicoding.movieapp.data.source.local.LocalDataSource
import com.dicoding.movieapp.data.source.local.room.MovieAppDatabase
import com.dicoding.movieapp.data.source.remote.RemoteDataSource
import com.dicoding.movieapp.utils.AppExecutors
import com.dicoding.movieapp.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): MovieAppRepository{

        val database = MovieAppDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))
        val localDataSource = LocalDataSource.getInstance(database.movieAppDao())
        val appExecutors = AppExecutors()

        return MovieAppRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}