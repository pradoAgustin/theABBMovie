package com.agustin.abbmovie.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.graphics.Palette
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agustin.abbmovie.*
import com.agustin.abbmovie.Detail.DetailMovieActivity
import com.agustin.abbmovie.splash.CurrentConfiguration
import com.agustin.abbmovie.splash.SplashActivity
import kotlinx.android.synthetic.main.activity_fullscreen.*
import kotlinx.android.synthetic.main.movie_search_row.view.*


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class MovieSearchActivity : AbstractRecyclerActivity() {

    private val searchMovieViewModel: SearchMovieViewModel by lazy {
        ViewModelProviders.of(this).get(SearchMovieViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchMovieViewModel.searchForTrendingMovie().observe(this, Observer { mainActivityProductsResponse ->
            initializeUI(mainActivityProductsResponse)
        })
    }

    override fun getLayoutResourceId(): Int {
       return  R.layout.activity_fullscreen
    }

    private fun initializeUI(mainActivityProductsResponse: SearchMovieResponse?) {
        recyclerView.adapter = SearchMoviesAdapter(mainActivityProductsResponse?.results)
        recyclerView.layoutManager = LinearLayoutManager(this)
        setRecyclerViewScrollListener()
    }

    companion object {

        fun startWithClearTop(activity: SplashActivity) {
            val intent = Intent(activity, MovieSearchActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            activity.startActivity(intent)
        }
    }

    inner class SearchMoviesAdapter(list: List<Movie>?) : MovieRecyclerAdapter<Movie, SearchMovieViewHolder>(list) {

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SearchMovieViewHolder {
            val inflatedView = LayoutInflater.from(p0.context).inflate(R.layout.movie_search_row, p0, false)
            return SearchMovieViewHolder(inflatedView)
        }

    }


    inner class SearchMovieViewHolder(inflatedView: View) : MovieRecyclerViewHolder<Movie>(inflatedView) {
        init {
            initialize(inflatedView)
        }

        override fun bindItem(item: Movie) {
            view.movieTitle.text = item.title
            if(item.hasGenres()) {
                view.movieGenre.text = CurrentConfiguration.getGenReById(item.genreIds[0])
            }
            val poster = item.posterPath
            poster?.also {
                GlideApi.loadImage(view.moviePoster, it, CurrentConfiguration.getOriginalSizeConfiguration())
            }
        }

        override fun onClick(v: View?) {
            val recyclerAdapter : SearchMoviesAdapter = recyclerView.adapter as SearchMoviesAdapter

            val drawable = view.moviePoster.drawable as BitmapDrawable
            val bitmap = drawable.bitmap

            val palette = Palette.from(bitmap).generate()
            val swatch1 = palette.darkVibrantSwatch
            val color = swatch1?.rgb
            DetailMovieActivity.start(this@MovieSearchActivity,recyclerAdapter.getItem(adapterPosition),color)
            //NewsActivity.start(this@MovieSearchActivity)
        }
    }

}
