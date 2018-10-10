package com.agustin.abbmovie

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.widget.ImageView
import com.agustin.abbmovie.splash.MovieBasicConfiguration
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


object GlideApi {

 private lateinit var movieBasicConfiguration: MovieBasicConfiguration


   private fun loadImage(view: ImageView, url: String) {
        Glide.with(view.context)
                .load(url)
                .apply(RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_background))
                .into(view)
    }


    fun loadImage(moviePoster: ImageView, poster: String, originalSizeUrl: String) {

        loadImage(moviePoster,originalSizeUrl + poster)
    }

    fun loadImageWithBlackAndWhiteFilter(moviePoster : ImageView, poster: String, originalSizeUrl: String){
        val matrix = ColorMatrix()
        matrix.setSaturation(0f)
        val filter = ColorMatrixColorFilter(matrix)
        moviePoster.colorFilter = filter

        loadImage(moviePoster,poster,originalSizeUrl)
    }
}