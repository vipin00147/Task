package com.example.test_task.ui.activities

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.example.test_task.R
import com.example.test_task.base.BaseActivity
import com.example.test_task.databinding.ActivityMainBinding
import com.example.test_task.repository.ProductRepository.Companion.repository
import com.example.test_task.ui.activities.fragments.HomeFragment
import com.example.test_task.utils.changeFragment

class MainActivity<T> : BaseActivity<ActivityMainBinding>() {

    override fun createBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setLoaderDialogView()
        repository.initContext(this as MainActivity<ViewBinding>)
        initHomeActivity(this as MainActivity<ViewBinding>)
        changeTopBarColor(resources.getColor(R.color.white))
        changeStatusBarIconColorToBlack(binding.root)
        HomeFragment().changeFragment(R.id.mainContainer, this, false)

    }
}