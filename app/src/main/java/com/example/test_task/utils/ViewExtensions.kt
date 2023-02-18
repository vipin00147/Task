package com.example.test_task.utils

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Environment
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


fun Fragment.changeFragment(containerId: Int, requireActivity: FragmentActivity, isAddToBakStack : Boolean) {

    var fragment = this
    val transaction = requireActivity.supportFragmentManager.beginTransaction()
    transaction.let {
        it.replace(containerId, fragment)
        if(isAddToBakStack) {
            it.addToBackStack(this.javaClass.canonicalName)
        }
        it.commit()
    }
}

inline fun <reified T : Activity> Activity.startIntentActivity(setFinish : Boolean) {
    startActivity(Intent(this, T::class.java))
    if(setFinish) {
        finish()
    }
}

@BindingAdapter("setYearText")
fun setYearText(textView : AppCompatTextView, year: String) {
    textView.text = "Year $year"
}

@BindingAdapter("imageUrl")
fun setImageUrl(imageView : ShapeableImageView, imageUri: String?) {

    val circularProgressDrawable = CircularProgressDrawable(imageView.context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    Glide.with(imageView)
        .load(imageUri)
        .placeholder(circularProgressDrawable)
        .into(imageView)
}