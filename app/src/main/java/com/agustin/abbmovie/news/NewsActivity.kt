package com.agustin.abbmovie.news

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agustin.abbmovie.*
import com.agustin.abbmovie.search.SearchMovieResponse
import com.agustin.abbmovie.search.SearchMovieViewModel
import com.agustin.abbmovie.splash.CurrentConfiguration
import kotlinx.android.synthetic.main.news_activity.*
import kotlinx.android.synthetic.main.news_recycler_row.view.*

class NewsActivity : AbstractRecyclerActivity(), SearchListener {
    private var newsAdapter: NewsMoviesAdapter? = null
    private var queryPage: Int = 0
    private var maxQueryPageAllowed = 0
    private lateinit var queryParam: String
    private val searchMovieViewModel: SearchMovieViewModel by lazy {
        ViewModelProviders.of(this).get(SearchMovieViewModel::class.java)
    }

    override fun onLastItemShown(lastVisibleItemPosition: Int) {
        searchMovieViewModel.takeIf { queryPage < maxQueryPageAllowed }
                ?.searchForMovies(queryParam = queryParam, page = queryPage + 1)
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.news_activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState != null) {
            queryPage = savedInstanceState[QUERY_PAGE_EXTRA] as Int
            queryParam = savedInstanceState[QUERY_PARAM_EXTRA] as String
            maxQueryPageAllowed = savedInstanceState[QUERY_MAX_PAGE_EXTRA] as Int
        }
    }

    override fun initializeCustomSearchViewIfRequired() {
        customSearchView.initializeSearchListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putSerializable(QUERY_MAX_PAGE_EXTRA, maxQueryPageAllowed)
        outState?.putSerializable(QUERY_PAGE_EXTRA, queryPage)
        outState?.putSerializable(QUERY_PARAM_EXTRA, queryParam)
        super.onSaveInstanceState(outState)
    }

    override fun onSearchRequested(text: String) {
        searchMovieViewModel.searchForMovies(text).observe(this, Observer { moviesResponse ->
            initializeUI(moviesResponse)
        })
    }

    private fun initializeUI(moviesResponse: SearchMovieResponse?) {
        if (newsAdapter != null) {
            newsAdapter?.updateItems(moviesResponse?.results)
        } else {
            newsAdapter = NewsMoviesAdapter(moviesResponse?.results)
            recycler.layoutManager = LinearLayoutManager(this)
            recycler.adapter = newsAdapter
        }

        queryPage = moviesResponse?.page ?: 0
        maxQueryPageAllowed = moviesResponse?.totalPages ?: 0
    }

    override fun onCancelRequested() {
        //do nothing
    }


    companion object {
        const val QUERY_PAGE_EXTRA = "QUERY_PAGE_EXTRA"
        const val QUERY_MAX_PAGE_EXTRA = "QUERY_MAX_PAGE_EXTRA"
        const val QUERY_PARAM_EXTRA = "QUERY_PARAM_EXTRA"

        fun start(activity: AppCompatActivity) {
            val intent = Intent(activity, NewsActivity::class.java)
            activity.startActivity(intent)
        }
    }

    inner class NewsMoviesAdapter(list: List<Movie>?) : MovieRecyclerAdapter<Movie, NewsActivity.NewsRecyclerViewHolder>(list) {

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
            if (item.hasGenres()) {
                view.newsGenre.text = CurrentConfiguration.getGenReById(item.genreIds[0])?.toUpperCase()
                view.sucriptionState.setText(R.string.notScuscribed)
                view.isSelected = false

                view.sucriptionState.setOnClickListener {
                    if (it.isSelected) {
                        it.isSelected = false
                        view.sucriptionState.setText(R.string.notScuscribed)
                    } else {
                        it.isSelected = true
                        view.sucriptionState.setText(R.string.suscribed)
                    }
                }

            }
            val poster = item.posterPath
            poster?.also {
                GlideApi.loadImage(view.newsImage, it, CurrentConfiguration.getOriginalSizeConfiguration())
            }
        }

        override fun onClick(v: View?) {

        }
    }

}