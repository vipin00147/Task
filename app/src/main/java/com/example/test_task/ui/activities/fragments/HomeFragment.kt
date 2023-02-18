package com.example.test_task.ui.activities.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.test_task.R
import com.example.test_task.base.BaseFragment
import com.example.test_task.databinding.FragmentHomeBinding
import com.example.test_task.model.MovieData
import com.example.test_task.repository.ProductRepository.Companion.repository
import com.example.test_task.utils.EndlessRecyclerViewScroll
import com.example.test_task.utils.GridItemDecoration
import com.example.test_task.viewModel.HomeViewModel
import com.example.test_task.viewModel.ViewModelFactory


class HomeFragment : BaseFragment<FragmentHomeBinding>(), TextWatcher {

    private var homeViewModel : HomeViewModel ?= null
    private val movieList = ArrayList<MovieData>()
    private var totalMovies : Int = 0
    private var rvAdapter = ProductAdapter(movieList)
    var currentPage = 1

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiateViewModel()
        setRecyclerViewAdapter()
        getImageList(currentPage, true)
        getObservers()
        binding?.searchView?.addTextChangedListener(this)

    }

    private fun setRecyclerViewAdapter() {
        binding?.movieRecyclerView?.adapter = rvAdapter
        binding?.movieRecyclerView?.addOnScrollListener(endlessRecyclerViewScroll)
        val itemDecoration = GridItemDecoration(getBaseActivity(), R.dimen.grid_space)
        binding?.movieRecyclerView?.addItemDecoration(itemDecoration)
    }

    private fun initiateViewModel() {
        homeViewModel = ViewModelProviders.of(this, ViewModelFactory(getBaseActivity()))[HomeViewModel::class.java]
    }

    private fun getImageList(currentPage: Int, showLoader : Boolean) {
        homeViewModel?.getImageList(currentPage, showLoader)
    }

    private fun getObservers() {
        repository.isProgressShowing.observe(viewLifecycleOwner, Observer {
            when(it) {
                true -> {
                    getBaseActivity().showLoader()
                }
                false -> {
                    getBaseActivity().hideLoader()
                }
            }
        })

        homeViewModel?.videoData?.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                totalMovies = it.totalResults.toString().toInt()
                val oldSize = movieList.size
                movieList.addAll(it.movieData!!)
                rvAdapter.updateRange(movieList, oldSize,movieList.size)

                homeViewModel?.videoData?.value = null
                binding?.loadMore?.hideView()
            }
        })

        homeViewModel?.errorOccur?.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                with(binding?.loadMore!!) {

                    if(it) {
                        this.hideView()
                    }
                }

                homeViewModel?.errorOccur?.value = null
            }
        })
    }

    private var endlessRecyclerViewScroll = object : EndlessRecyclerViewScroll() {

        override fun onLoadMore(page_no: Int) {

            if (movieList.size < totalMovies) {
                binding?.loadMore?.showView()
                getImageList(++currentPage, false)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        Runtime.getRuntime().gc()
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { Unit }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { Unit }

    override fun afterTextChanged(p0: Editable?) {
        filterMovies(p0.toString())
    }

    private fun filterMovies(text: String) {
        val temp: MutableList<MovieData> = ArrayList()

        for (data in movieList) {
            if (data.title?.toLowerCase()?.contains(text.toLowerCase())!!) {
                temp.add(data)
            }
        }

        rvAdapter.updateList(temp)
    }

}