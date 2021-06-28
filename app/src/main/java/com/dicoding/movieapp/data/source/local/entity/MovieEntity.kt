package com.dicoding.movieapp.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieentities")
data class MovieEntity (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movieId")
    var id: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "genre")
    var genre: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "year")
    var year: String,

    @ColumnInfo(name = "cast")
    var cast: String,

    @ColumnInfo(name = "favorite")
    var favorite:Boolean = false,

    @ColumnInfo(name = "imagePath")
    var imagePath: String


)