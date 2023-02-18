package com.example.test_task.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.example.test_task.base.BaseActivity
import com.example.test_task.model.MovieModel
import com.example.test_task.repository.ProductRepository.Companion.repository

class HomeViewModel(val baseActivity: BaseActivity<ViewBinding>) : ViewModel(){

    val videoData : MutableLiveData<MovieModel> = MutableLiveData()
    val errorOccur : MutableLiveData<Boolean> = MutableLiveData()

    fun getImageList(currentPage: Int, showLoader: Boolean) {
        repository.getVideoList(videoData, currentPage, showLoader, errorOccur)
    }

 }