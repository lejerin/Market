package com.example.shopping.util

import android.widget.ImageView
import android.widget.RatingBar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.shopping.R


@BindingAdapter("android:src")
fun setImageViewResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}

@BindingAdapter("image")
fun loadImageview(view : ImageView, url: String?){
    if(url != null){
        Glide.with(view)
            .load(url)
            .placeholder(R.drawable.gray)
            .fitCenter()
            .centerCrop()
            .into(view)
    }
}



