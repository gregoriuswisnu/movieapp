package com.dicoding.movieapp.ui.favorite.favseries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.movieapp.R
import com.dicoding.movieapp.databinding.FragmentFavSeriesBinding
import com.dicoding.movieapp.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar


class FavSeries : Fragment() {

    private lateinit var binding: FragmentFavSeriesBinding
    private lateinit var viewModel: FavSerieViewModel
    private lateinit var adapter: FavSerieAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavSeriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding.rvFavSerie)
        if (activity != null){
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[FavSerieViewModel::class.java]

            adapter = FavSerieAdapter()
            binding.progressBar.visibility = View.VISIBLE
            viewModel.getFavSeries().observe(viewLifecycleOwner, { series ->
                binding?.progressBar.visibility = View.GONE
                adapter.submitList(series)
            })

            binding.rvFavSerie.layoutManager = LinearLayoutManager(context)
            binding.rvFavSerie.setHasFixedSize(true)
            binding.rvFavSerie.adapter = adapter
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback(){
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        }

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null){
                val swipedPosition = viewHolder.adapterPosition
                val seriesEntity = adapter.getSwipedData(swipedPosition)
                seriesEntity?.let { viewModel.setFavSeries(it) }

                val snackbar =Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.message_ok){ v ->
                    seriesEntity?.let { viewModel.setFavSeries(it) }
                }
                snackbar.show()
            }
        }

    })


}