package com.dicoding.movieapp.ui.series

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.movieapp.databinding.FragmentSeriesBinding
import com.dicoding.movieapp.viewmodel.ViewModelFactory
import com.dicoding.movieapp.vo.Status


class SeriesFragment : Fragment() {
    private lateinit var fragmentSeriesBinding: FragmentSeriesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentSeriesBinding = FragmentSeriesBinding.inflate(inflater, container, false)
        return fragmentSeriesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity !=null){
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[SeriesViewModel::class.java]
//            val series = viewModel.getSeries()
            val seriesAdapter = SeriesAdapter()
            fragmentSeriesBinding.progressBar.visibility = View.VISIBLE

            viewModel.getSeries().observe(viewLifecycleOwner, Observer { series ->
                if (series != null){
                    when(series.status){
                        Status.LOADING -> fragmentSeriesBinding.progressBar.visibility = View.VISIBLE
                        Status.SUCCESS ->{
                            fragmentSeriesBinding.progressBar.visibility = View.GONE
                            seriesAdapter.submitList(series.data)
                        }
                        Status.ERROR ->{
                            fragmentSeriesBinding.progressBar.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

            with(fragmentSeriesBinding.rvSeries){
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = seriesAdapter
            }
        }
    }


}