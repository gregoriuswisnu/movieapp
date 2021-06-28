package com.dicoding.movieapp.data.source.remote

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.movieapp.data.source.remote.response.MovieResponse
import com.dicoding.movieapp.data.source.remote.response.SeriesResponse
import com.dicoding.movieapp.utils.EspressoIdlingResources
import com.dicoding.movieapp.utils.JsonHelper

class RemoteDataSource private constructor(private val jsonHelper: JsonHelper){

    private val handler = Handler(Looper.getMainLooper())

    companion object{
        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: JsonHelper): RemoteDataSource =
            instance ?: synchronized(this){
                instance ?: RemoteDataSource(helper).apply { instance = this }
            }
    }

    fun getAllMovies(): LiveData<ApiResponse<List<MovieResponse>>>{
        EspressoIdlingResources.increment()
        val resultMovie = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        handler.postDelayed({
            resultMovie.value = ApiResponse.success(jsonHelper.loadMovie())
                            EspressoIdlingResources.decrement()},
            SERVICE_LATENCY_IN_MILLIS)
        return resultMovie
    }

    fun getAllSeries(): LiveData<ApiResponse<List<SeriesResponse>>>{
        EspressoIdlingResources.increment()
        val resultSeries = MutableLiveData<ApiResponse<List<SeriesResponse>>>()
        handler.postDelayed({
            resultSeries.value = ApiResponse.success(jsonHelper.loadSeries())
                            EspressoIdlingResources.decrement()}, SERVICE_LATENCY_IN_MILLIS)
        return resultSeries
    }


}