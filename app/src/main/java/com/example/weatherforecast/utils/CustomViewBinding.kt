package com.example.weatherforecast.utils

import android.annotation.SuppressLint
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.weatherforecast.R


object CustomViewBinding {

    @SuppressLint("CheckResult")
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun imageUrl(
        imageView: AppCompatImageView,
        iconUrl: String?
    ) {
        iconUrl?.let { url ->
            Glide
                .with(imageView.context)
                .load(
                    String.format(
                        imageView.context.getString(
                            R.string.open_weather_img_url,
                            url
                        )
                    )
                ).into(imageView)
        }

    }


}