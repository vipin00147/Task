package com.example.test_task.ui.activities.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test_task.databinding.ProductListItemBinding
import com.example.test_task.model.MovieData

class ProductAdapter(var movieList: ArrayList<MovieData>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    fun updateList(list: List<MovieData>) {
        movieList = list as ArrayList<MovieData>
        notifyDataSetChanged()
    }

    fun updateRange(list: List<MovieData>, oldSize : Int, newSize : Int) {
        movieList = list as ArrayList<MovieData>
        notifyItemRangeInserted(oldSize, newSize)
    }

    inner class ViewHolder(val binding: ProductListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder){
            with(movieList[position]) {
                binding.model = this
                binding.root.setOnClickListener {  }
            }
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}
