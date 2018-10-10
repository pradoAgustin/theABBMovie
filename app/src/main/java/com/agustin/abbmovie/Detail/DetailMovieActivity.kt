package com.agustin.abbmovie.Detail

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.graphics.ColorUtils
import android.support.v7.app.AppCompatActivity
import com.agustin.abbmovie.GlideApi
import com.agustin.abbmovie.Movie
import com.agustin.abbmovie.R
import com.agustin.abbmovie.splash.CurrentConfiguration
import kotlinx.android.synthetic.main.detail_collapsable.*


class DetailMovieActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {
    private val PERCENTAGE_TO_ANIMATE_COLLAPSING_MOVIE = 20
    private var shouldCollapseMovie = true
    private var mMaxScrollSize: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_collapsable)
        val movie: Movie = intent.extras[MOVIE_EXTRA] as Movie
        val posterPath = movie.posterPath
        flexible_example_appbar.addOnOffsetChangedListener(this)
        mMaxScrollSize = flexible_example_appbar.getTotalScrollRange()
        if (posterPath != null) {
            GlideApi.loadImage(collapseImage, posterPath, CurrentConfiguration.getOriginalSizeConfiguration())
            GlideApi.loadImage(backImg, posterPath, CurrentConfiguration.getOriginalSizeConfiguration())
        }

        val color: Int? = intent.extras[COLOR_EXTRA] as Int?

        if (color != null) {
            val transparency = ColorUtils.setAlphaComponent(color, 128)
            //annonce_main_coordinator.alpha =
        }

        if (posterPath != null) {
            // GlideApi.loadImage(moviePoster, posterPath, CurrentConfiguration.getOriginalSizeConfiguration())
        }


       // movieTitle.text = movie.title
       // subtitle.text = movie.releaseDate
        // overView.text = movie.overview

    }


    companion object {
        const val MOVIE_EXTRA = "MOVIE_EXTRA"
        const val COLOR_EXTRA = "COLOR_EXTRA"
        fun start(activity: Activity, movieExtra: Movie, color: Int?) {
            val intent = Intent(activity, DetailMovieActivity::class.java)
            intent.putExtra(MOVIE_EXTRA, movieExtra)
            intent.putExtra(COLOR_EXTRA, color)
            activity.startActivity(intent)
        }

    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, i: Int) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.totalScrollRange

        val percentage = Math.abs(i) * 100 / mMaxScrollSize


        if (percentage >= PERCENTAGE_TO_ANIMATE_COLLAPSING_MOVIE && shouldCollapseMovie) {
            shouldCollapseMovie = false

            collapseImage.animate().scaleY(0.4f).scaleX(0.4f).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                }
            }).setDuration(200)
                    .start()
        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_COLLAPSING_MOVIE && !shouldCollapseMovie) {
            shouldCollapseMovie = true
            collapseImage.animate()
                    .scaleY(1f).scaleX(1f)
                    .start()
        }
    }


}