package com.dicoding.movieapp.utils

import android.content.Context
import com.dicoding.movieapp.data.source.remote.response.MovieResponse
import com.dicoding.movieapp.data.source.remote.response.SeriesResponse
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class JsonHelper(private val context: Context) {

    private fun parsingFileToString(fileName: String): String?{
        return try {
            val `is` = context.assets.open(fileName)
            val buffer = ByteArray(`is`.available())
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        }catch (ex: IOException){
            ex.printStackTrace()
            null
        }
    }

    fun loadMovie(): List<MovieResponse>{
        val list = ArrayList<MovieResponse>()
        try {
            val responseObject = JSONObject(parsingFileToString("Movie.json").toString())
            val listArray = responseObject.getJSONArray("movies")
            for (i in 0 until listArray.length()){
                val movie = listArray.getJSONObject(i)

                val id = movie.getString("id")
                val title = movie.getString("title")
                val genre = movie.getString("genre")
                val description = movie.getString("description")
                val year = movie.getString("year")
                val cast = movie.getString("cast")
                val imagePath = movie.getString("imagePath")


                val movieResponse = MovieResponse(id, title, genre, description, year, cast, imagePath)
                list.add(movieResponse)
            }
        }catch (e: JSONException){
            e.printStackTrace()
        }
        return list
    }

    fun loadSeries(): List<SeriesResponse>{
        val list = ArrayList<SeriesResponse>()
        try {
            val responseObject = JSONObject(parsingFileToString("Series.json").toString())
            val listArray = responseObject.getJSONArray("series")
            for (i in 0 until listArray.length()){
                val serie = listArray.getJSONObject(i)

                val id = serie.getString("id")
                val title = serie.getString("title")
                val description = serie.getString("description")
                val year = serie.getString("year")
                val imagePath = serie.getString("imagePath")
                val genre = serie.getString("genre")
                val cast = serie.getString("cast")

                val seriesResponse = SeriesResponse(id,title,description,year,imagePath,genre, cast)
                list.add(seriesResponse)
            }
        }catch (e: JSONException){
            e.printStackTrace()
        }
        return list
    }
}