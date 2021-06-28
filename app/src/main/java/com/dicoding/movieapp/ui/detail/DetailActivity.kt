package com.dicoding.movieapp.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.movieapp.R
import com.dicoding.movieapp.data.source.local.entity.MovieEntity
import com.dicoding.movieapp.data.source.local.entity.SeriesEntity
import com.dicoding.movieapp.databinding.ActivityDetailBinding
import com.dicoding.movieapp.databinding.ContentDetailBinding
import com.dicoding.movieapp.viewmodel.ViewModelFactory
import com.dicoding.movieapp.vo.Status


class DetailActivity : AppCompatActivity() {

    companion object{
        //untuk ambil item
        const val EXTRA_ITEM = "extra_item"
        //untuk pilih tab
        const val EXTRA_TITLE = "extra_title"
    }

    private lateinit var activityDetailBinding: ActivityDetailBinding
    private lateinit var detailBinding: ContentDetailBinding

    private lateinit var viewModel: DetailViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        detailBinding = activityDetailBinding.detailContent

        setContentView(activityDetailBinding.root)
        setSupportActionBar(activityDetailBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras


        if (extras != null){
            val movieId = extras.getString(EXTRA_ITEM)
            val title = extras.getString(EXTRA_TITLE)
            if (title.equals(R.string.movie.toString(), ignoreCase = true)){
                setSupportActionBar(findViewById(R.id.toolbar))

                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                if (movieId != null){
                    viewModel.setSelectedItem(movieId)
                    viewModel.movieWithId.observe(this, Observer { movies ->
                        if (movies != null){
                            val state = movies.data?.favorite
                            if (state != null) {
                                setFavorite(state)
                            }
                            when(movies.status){
                                Status.LOADING -> activityDetailBinding.progressBar.visibility = View.VISIBLE
                                Status.SUCCESS ->{
                                    if (movies.data != null){
                                        activityDetailBinding.progressBar.visibility = View.GONE
                                        populateMovie(movies.data!!)
                                        activityDetailBinding.fabFavorite.setOnClickListener {
                                            if (state != null) {
                                                setFavorite(state)
                                            }
                                            viewModel.setFavoriteMovie()
                                        }
                                    }
                                }
                                Status.ERROR ->{
                                    activityDetailBinding.progressBar.visibility = View.GONE
                                    Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    })
                }
            }
            if (title.equals(R.string.tv_series.toString(), ignoreCase = true)){
                if (movieId != null){
                    viewModel.setSelectedItem(movieId)
                    viewModel.serieWithId.observe(this, Observer { series ->
                        if (series != null){
                            val state = series.data?.favorite
                            if (state != null) {
                                setFavorite(state)
                            }
                            when(series.status){
                                Status.LOADING -> activityDetailBinding.progressBar.visibility = View.VISIBLE
                                Status.SUCCESS ->{
                                    if (series.data != null){
                                        activityDetailBinding.progressBar.visibility =View.GONE
                                        populateSeries(series.data!!)
                                        activityDetailBinding.fabFavorite.setOnClickListener {
                                            if (state != null) {
                                                setFavorite(state)
                                            }
                                            viewModel.setFavoriteSerie()
                                        }
                                    }
                                }
                                Status.ERROR ->{
                                    activityDetailBinding.progressBar.visibility = View.GONE
                                    Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                    })
                }
            }
        }
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setFavorite(state: Boolean) {
        if (state == false){
            activityDetailBinding.fabFavorite.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_border_24))

        }else{
            activityDetailBinding.fabFavorite.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_24))


        }
    }


    private fun populateMovie(movieEntity: MovieEntity){
        detailBinding.apply {
            textTitleDesc.text = movieEntity.title
            textCastDetail.text = movieEntity.cast
            textDescription.text = movieEntity.description
            textGenreDesc.text = movieEntity.genre
            textYearDesc.text = movieEntity.year

            Glide.with(this@DetailActivity)
                    .load(movieEntity.imagePath)
                    .into(imagePoster)
        }
    }

    private fun populateSeries(seriesEntity: SeriesEntity){
        detailBinding.apply {
            textTitleDesc.text = seriesEntity.title
            textCastDetail.text = seriesEntity.cast
            textDescription.text = seriesEntity.description
            textGenreDesc.text = seriesEntity.genre
            textYearDesc.text = seriesEntity.year

            Glide.with(this@DetailActivity)
                    .load(seriesEntity.imagePath)
                    .into(imagePoster)
        }
    }


}