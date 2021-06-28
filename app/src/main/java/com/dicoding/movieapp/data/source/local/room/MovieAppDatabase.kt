package com.dicoding.movieapp.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.movieapp.data.source.local.entity.MovieEntity
import com.dicoding.movieapp.data.source.local.entity.SeriesEntity

@Database(entities = [MovieEntity::class, SeriesEntity::class],
version = 1, exportSchema = false)
abstract class MovieAppDatabase: RoomDatabase() {
    abstract fun movieAppDao(): MovieAppDao

    companion object{

        @Volatile
        private var INSTANCE: MovieAppDatabase? = null

        fun getInstance(context: Context): MovieAppDatabase =
            INSTANCE ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    MovieAppDatabase::class.java,
                    "MovieApp.db"
                ).build().apply {
                    INSTANCE = this
                }
            }
    }
}