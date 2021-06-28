package com.dicoding.movieapp.data.source.remote.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class MovieResponse (
    var id: String,
    var title: String,
    var genre: String,
    var description: String,
    var year: String,
    var cast: String,
    var imagePath: String
): Parcelable