package com.sunasterisk.fooddaily.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sunasterisk.fooddaily.R

val requestOptions = RequestOptions().apply {
    transform(CenterCrop(), RoundedCorners(Constants.RADIUS_IMAGE))
}

fun ImageView.applyGlide(url: String?) {
    Glide.with(context)
        .load(url)
        .apply(requestOptions)
        .placeholder(R.drawable.ic_broken_image)
        .into(this)
}
