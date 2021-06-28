package com.dicoding.movieapp.ui.favorite.favseries

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

class FavSerieAdapter: PagedListAdapter<SeriesEntity, FavSerieAdapter.FavSerieHolder>(DIFF_CALLBACK)  {

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
//    private var listFavSeries = ArrayList<SeriesEntity>()
//
//    fun setFavSerie(series: List<SeriesEntity>?){
//        if (series == null) return
//        this.listFavSeries.clear()
//        this.listFavSeries.addAll(series)
//    }

    inner class FavSerieHolder(private val binding: ItemSeriesListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(serie: SeriesEntity){
            with(binding){
                tvItemTitle.text = serie.title
                tvItemYear.text = serie.year

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_ITEM, serie.id)
                    intent.putExtra(DetailActivity.EXTRA_TITLE, R.string.tv_series.toString())
                    itemView.context.startActivity(intent)
                }

                Glide.with(itemView.context)
                    .load(serie.imagePath)
                    .into(imgPoster)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavSerieHolder {
        val itemFavSerieBinding = ItemSeriesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavSerieHolder(itemFavSerieBinding)
    }

    override fun onBindViewHolder(holder: FavSerieHolder, position: Int) {
        val serie = getItem(position)
        if (serie != null){
            holder.bind(serie)
        }
    }
    fun getSwipedData(swipedPostion: Int): SeriesEntity? = getItem(swipedPostion)

//    override fun getItemCount(): Int {
//        return listFavSeries.size
//    }
}