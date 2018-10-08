package com.agustin.abbmovie

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_fullscreen.*
import kotlinx.android.synthetic.main.movie_search_row.view.*
import android.support.v7.graphics.Palette
import com.agustin.abbmovie.splash.SplashActivity


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class MovieSearchActivity : AppCompatActivity() {

    private val searchMovieViewModel: SearchMovieViewModel by lazy {
        ViewModelProviders.of(this).get(SearchMovieViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)
        supportActionBar?.hide()

        searchMovieViewModel.searchForMovies().observe(this, Observer { mainActivityProductsResponse ->
            initializeUI(mainActivityProductsResponse)
        })
    }

    private fun initializeUI(mainActivityProductsResponse: SearchMovieResponse?) {
        Log.d("movies", mainActivityProductsResponse?.toString())
        recyclerView.adapter = SearchMoviesAdapter(mainActivityProductsResponse?.results)
        recyclerView.layoutManager = LinearLayoutManager(this)
        setRecyclerViewScrollListener()
    }


    private fun requestMoreMovies(lastVisibleItemPosition: Int) {
        searchMovieViewModel.requestMoreMovies(lastVisibleItemPosition).observe(this, Observer { searchMoviesResponse ->
            updateProductsRecyclerView(searchMoviesResponse)
        })
    }

    private fun updateProductsRecyclerView(searchMoviesResponse: SearchMovieResponse?) {
        val adapter : SearchMoviesAdapter = recyclerView.adapter as SearchMoviesAdapter
        adapter.updateItems(searchMoviesResponse?.results)

    }

    private fun setRecyclerViewScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val linearLayoutManager = recyclerView!!.layoutManager as LinearLayoutManager
                val totalItemCount = linearLayoutManager.itemCount
                val lastItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                val lastVisibleItemPosition = lastItemPosition + 1
                if (totalItemCount == lastVisibleItemPosition) {
                    requestMoreMovies(lastVisibleItemPosition)
                }
            }
        })
    }

    companion object {

        fun startWithClearTop(activity: SplashActivity) {
            val intent = Intent(activity,MovieSearchActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            activity.startActivity(intent)
        }
    }

    inner class SearchMoviesAdapter(list: List<Movie>?) : MovieRecyclerAdapter<Movie, SearchMovieViewHolder>(list) {

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SearchMovieViewHolder {
            val inflatedView = LayoutInflater.from(p0.context).inflate(R.layout.movie_search_row, p0, false)
            return SearchMovieViewHolder(inflatedView)
        }

        fun updateItems(list: List<Movie>?) {
            onItemsInserted(list)
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
                GlideApi.loadImage(view.moviePoster,it,CurrentConfiguration.getOriginalSizeConfiguration())
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
        }
    }

}
