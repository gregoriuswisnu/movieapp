package com.dicoding.movieapp.data.source.remote.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SeriesResponse (
    var id: String,
    var title: String,
    var description: String,
    var year: String,
    var imagePath: String,
    var genre: String,
    var cast: String
):Parcelable