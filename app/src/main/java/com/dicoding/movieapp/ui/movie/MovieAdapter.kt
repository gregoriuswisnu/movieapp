package com.dicoding.movieapp.ui.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.movieapp.R
import com.dicoding.movieapp.data.source.local.entity.MovieEntity
import com.dicoding.movieapp.databinding.ItemMoviesListBinding
import com.dicoding.movieapp.ui.detail.DetailActivity

class MovieAdapter: PagedListAdapter<MovieEntity, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    companion object{
        private val DIFF_CALLBACK = object  : DiffUtil.ItemCallback<MovieEntity>(){
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

//    private var listMovies = ArrayList<MovieEntity>()
//
//    fun setMovies(movies: List<MovieEntity>?){
//        if (movies == null)return
//        this.listMovies.clear()
//        this.listMovies.addAll(movies)
//    }

    inner class MovieViewHolder(private val binding: ItemMoviesListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity){
            with(binding){
                tvItemTitle.text = movie.title
                tvItemDate.text = movie.year

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_ITEM, movie.id)
                    intent.putExtra(DetailActivity.EXTRA_TITLE, R.string.movie.toString())
                    itemView.context.startActivity(intent)
                }
                Glide.with(itemView.context)
                        .load(movie.imagePath)
                        .into(imgPoster)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemMoviesListBinding = ItemMoviesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemMoviesListBinding)
    }

//    override fun getItemCount(): Int = listMovies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null){
            holder.bind(movie)
        }
    }
}