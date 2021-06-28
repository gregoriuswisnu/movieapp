package com.dicoding.movieapp.ui.series

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.movieapp.R
import com.dicoding.movieapp.data.source.local.entity.SeriesEntity
import com.dicoding.movieapp.databinding.ItemSeriesListBinding
import com.dicoding.movieapp.ui.detail.DetailActivity

class SeriesAdapter: PagedListAdapter<SeriesEntity, SeriesAdapter.SeriesViewHolder>(DIFF_CALLBACK) {

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SeriesEntity>(){
            override fun areItemsTheSame(oldItem: SeriesEntity, newItem: SeriesEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SeriesEntity, newItem: SeriesEntity): Boolean {
                return oldItem == newItem
            }

        }
    }

//    private val listSeries = ArrayList<SeriesEntity>()
//
//    fun setSeries(series: List<SeriesEntity>?){
//        if (series == null)return
//        this.listSeries.clear()
//        this.listSeries.addAll(series)
//    }
    inner class SeriesViewHolder(private val binding: ItemSeriesListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(series: SeriesEntity){
            with(binding){
                tvItemTitle.text = series.title
                tvItemYear.text = series.year

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_ITEM, series.id)
                    intent.putExtra(DetailActivity.EXTRA_TITLE, R.string.tv_series.toString())
                    itemView.context.startActivity(intent)
                }
                Glide.with(itemView.context)
                        .load(series.imagePath)
                        .into(imgPoster)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val itemSeriesBinding = ItemSeriesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeriesViewHolder(itemSeriesBinding)
    }

//    override fun getItemCount(): Int = listSeries.size

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        val series = getItem(position)
        if (series != null){
            holder.bind(series)
        }
    }


}