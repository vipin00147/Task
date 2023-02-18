package com.example.test_task.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.test_task.base.BaseActivity

class ViewModelFactory(val baseActivity: BaseActivity<ViewBinding>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(baseActivity) as T
        }
        else {
            throw IllegalArgumentException("Unknown class name")
        }
    }
}