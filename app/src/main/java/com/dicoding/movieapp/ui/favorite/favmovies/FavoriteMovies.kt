package com.dicoding.movieapp.ui.favorite.favmovies

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
import com.dicoding.movieapp.databinding.FragmentFavoriteMoviesBinding
import com.dicoding.movieapp.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar


class FavoriteMovies : Fragment() {

    private lateinit var binding: FragmentFavoriteMoviesBinding
    private lateinit var viewModel: FavMovieViewModel
    private lateinit var adapter: FavMovieAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding.rvFavMovie)
        if (activity != null){
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[FavMovieViewModel::class.java]

            adapter = FavMovieAdapter()
            binding.progressBar.visibility = View.GONE
            viewModel.getFavMovies().observe(viewLifecycleOwner, { movies ->
            binding.progressBar.visibility = View.GONE
            adapter.submitList(movies)
            })
            binding.rvFavMovie.layoutManager = LinearLayoutManager(context)
            binding.rvFavMovie.setHasFixedSize(true)
            binding.rvFavMovie.adapter = adapter
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
                val swiperdPostion = viewHolder.adapterPosition
                val movieEntity = adapter.getSwipedData(swiperdPostion)
                movieEntity?.let { viewModel.setFavMovie(it) }

                val snackbar = Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.message_ok){ v ->
                    movieEntity?.let { viewModel.setFavMovie(it) }
                }
                snackbar.show()
            }
        }

    })


}