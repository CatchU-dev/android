package com.isaacsufyan.catchu.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

@BindingAdapter("imageUrl", "placeholder")
fun setImageResource(view: ImageView, url: String?, placeholder: Drawable) {
    if (!url.isNullOrEmpty()){

        val circularProgressDrawable = CircularProgressDrawable(view.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(view.context)
            .load(url)
            .override(300)
            .placeholder(circularProgressDrawable)
            .thumbnail(0.5f)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(view)
    }else{
        view.setImageDrawable(placeholder)
    }

}