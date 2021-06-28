package com.dicoding.movieapp.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.movieapp.databinding.FragmentMovieBinding
import com.dicoding.movieapp.viewmodel.ViewModelFactory
import com.dicoding.movieapp.vo.Status


class MovieFragment : Fragment() {
    private var _binding: FragmentMovieBinding? = null
    private val fragmentMovieBinding get() =  _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieBinding.inflate(inflater, container,false)
        return fragmentMovieBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null){

            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

//            val movies= viewModel.getMovies()
            val movieAdapter = MovieAdapter()
            viewModel.getMovies().observe(viewLifecycleOwner, Observer { movies ->
                if (movies != null){
                    when(movies.status){
                        Status.LOADING -> fragmentMovieBinding?.progressBar?.visibility = View.VISIBLE
                        Status.SUCCESS ->{
                            fragmentMovieBinding?.progressBar?.visibility = View.GONE
                            movieAdapter.submitList(movies.data)
                        }
                        Status.ERROR ->{
                            fragmentMovieBinding?.progressBar?.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }


            })

            with(fragmentMovieBinding?.rvMovie){
                this?.layoutManager = LinearLayoutManager(context)
                this?.setHasFixedSize(true)
                this?.adapter = movieAdapter
            }
        }
    }





}