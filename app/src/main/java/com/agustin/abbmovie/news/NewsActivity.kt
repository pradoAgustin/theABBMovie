package com.agustin.abbmovie.news

import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agustin.abbmovie.*
import com.agustin.abbmovie.search.MovieSearchActivity
import com.agustin.abbmovie.splash.CurrentConfiguration
import kotlinx.android.synthetic.main.activity_fullscreen.*
import kotlinx.android.synthetic.main.news_recycler_row.view.*

class NewsActivity : AppCompatActivity() {


    override fun setContentView(view: View?) {
        super.setContentView(R.layout.news_activity)
    }


    inner class NewsMoviesAdapter(list: List<Movie>?) : MovieRecyclerAdapter<Movie,NewsActivity.NewsRecyclerViewHolder>(list) {

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NewsActivity.NewsRecyclerViewHolder {
            val inflatedView = LayoutInflater.from(p0.context).inflate(R.layout.news_recycler_row, p0, false)
            return NewsRecyclerViewHolder(inflatedView)
        }

    }

    inner class NewsRecyclerViewHolder(inflatedView: View) : MovieRecyclerViewHolder<Movie>(inflatedView) {
        init {
            initialize(inflatedView)
        }

        override fun bindItem(item: Movie) {
            view.newstitle.text = item.title
            if(item.hasGenres()) {
                view.newsGenre.text = CurrentConfiguration.getGenReById(item.genreIds[0])
            }
            val poster = item.posterPath
            poster?.also {
                GlideApi.loadImage(view.newsImage, it, CurrentConfiguration.getOriginalSizeConfiguration())
            }
        }

        override fun onClick(v: View?) {
            val recyclerAdapter : MovieSearchActivity.SearchMoviesAdapter = recyclerView.adapter as MovieSearchActivity.SearchMoviesAdapter
        }
    }

}