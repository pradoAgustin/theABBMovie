package com.agustin.abbmovie.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.agustin.abbmovie.*
import com.agustin.abbmovie.database.AppDatabase
import com.agustin.abbmovie.database.MovieMatchEntity
import com.agustin.abbmovie.news.SearchTrendingMovieActivity
import com.agustin.abbmovie.splash.CurrentConfiguration
import kotlinx.android.synthetic.main.news_recycler_row.view.*
import kotlinx.android.synthetic.main.search_activity.*

class SearchMovieActivity : AbstractRecyclerActivity(), SearchListener {
    private var newsAdapter: NewsMoviesAdapter? = null
    private var queryPage: Int = 0
    private var maxQueryPageAllowed = 0
    private var queryParam: String = ""
    private lateinit var movieMatches: ArrayList<Int>
    private val searchMovieViewModel: SearchMovieViewModel by lazy {
        ViewModelProviders.of(this).get(SearchMovieViewModel::class.java)
    }

    override fun onLastItemShown(lastVisibleItemPosition: Int) {
        searchMovieViewModel.takeIf { queryPage < maxQueryPageAllowed }
                ?.searchForMovies(queryParam = queryParam, page = queryPage + 1)
                ?.observe(this, Observer { moviesResponse ->
                    initializeUI(moviesResponse)
                })
    }


    override fun getLayoutResourceId(): Int {
        return R.layout.search_activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            queryPage = savedInstanceState[QUERY_PAGE_EXTRA] as Int
            queryParam = savedInstanceState[QUERY_PARAM_EXTRA] as String
            maxQueryPageAllowed = savedInstanceState[QUERY_MAX_PAGE_EXTRA] as Int
            movieMatches = savedInstanceState[MATCHES_MOVIES_EXTRA] as ArrayList<Int>
        } else {
            movieMatches = CurrentConfiguration.getMovieMatches()
            initializeMatchesMoviesIfNeeded()
        }
        makeSearchIfQueryParamIsNotEmpty()

    }


    private fun makeSearchIfQueryParamIsNotEmpty() {
        if (queryParam.isNotBlank()) onSearchRequested(queryParam)
    }

    private fun initializeMatchesMoviesIfNeeded() {
        movieMatches = ArrayList(AppDatabase.getDatabase(applicationContext).movieMatchDao().getAllMoviesById())
    }

    override fun initializeCustomSearchViewIfRequired() {
        customSearchView.initializeSearchListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putSerializable(QUERY_MAX_PAGE_EXTRA, maxQueryPageAllowed)
        outState?.putSerializable(QUERY_PAGE_EXTRA, queryPage)
        outState?.putSerializable(QUERY_PARAM_EXTRA, queryParam)
        outState?.putSerializable(MATCHES_MOVIES_EXTRA, ArrayList(movieMatches))
        super.onSaveInstanceState(outState)
    }

    override fun onSearchRequested(text: String) {
        queryParam = text
        newsAdapter?.clearItems()
        searchMovieViewModel.searchForMovies(text).observe(this, Observer { moviesResponse ->
            initializeUI(moviesResponse)
        })
    }

    override fun onPause() {
        updateDBIfRequired()
        super.onPause()
    }

    private fun updateDBIfRequired() {
        val movieDao = AppDatabase.getDatabase(applicationContext).movieMatchDao()
        //we should have a helper in order to execute all DB related things
        val movieMatchesEntityList = ArrayList<MovieMatchEntity>()
        movieMatches.forEach { movieMatchesEntityList.add(MovieMatchEntity(it)) }
        movieDao.insertAll(movieMatchesEntityList)
    }

    private fun initializeUI(moviesResponse: SearchMovieResponse?) {
        showEmptyTextIfRequired(moviesResponse)
        if (newsAdapter != null) {
            newsAdapter?.updateItems(moviesResponse?.results)
        } else {
            newsAdapter = NewsMoviesAdapter(moviesResponse?.results)
            recycler.layoutManager = LinearLayoutManager(this)
            recycler.adapter = newsAdapter
            setRecyclerViewScrollListener(recycler)
        }

        queryPage = moviesResponse?.page ?: 0
        maxQueryPageAllowed = moviesResponse?.totalPages ?: 0
    }

    private fun showEmptyTextIfRequired(moviesResponse: SearchMovieResponse?) {
        val movies = moviesResponse?.results
        if (movies == null || movies.isEmpty()) {
            Toast.makeText(this, resources.getString(R.string.emptyQuery), Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCancelRequested() {
        // do nothing
    }


    companion object {
        const val QUERY_PAGE_EXTRA = "QUERY_PAGE_EXTRA"
        const val QUERY_MAX_PAGE_EXTRA = "QUERY_MAX_PAGE_EXTRA"
        const val QUERY_PARAM_EXTRA = "QUERY_PARAM_EXTRA"
        const val MATCHES_MOVIES_EXTRA = "MATCHES_MOVIES_EXTRA"

        fun start(activity: AppCompatActivity) {
            val intent = Intent(activity, SearchMovieActivity::class.java)
            activity.startActivity(intent)
        }

        fun startWithClearTop(activity: AppCompatActivity) {
            val intent = Intent(activity, SearchMovieActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            activity.startActivity(intent)
        }
    }

    inner class NewsMoviesAdapter(list: List<Movie>?) : MovieRecyclerAdapter<Movie, NewsRecyclerViewHolder>(list) {

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NewsRecyclerViewHolder {
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

                checkForMovieMatched(item.id)
                view.sucriptionState.setOnClickListener {
                    if (it.isSelected) {
                        onMovieNotSubscribed()
                        removeFromMatches(item.id)
                    } else {
                        onMovieSubscribed()
                        addToMatches(item.id)
                    }
                }

            }
            val poster = item.posterPath
            poster?.also {
                GlideApi.loadImage(view.newsImage, it, CurrentConfiguration.getOriginalSizeConfiguration())
            }
        }

        private fun addToMatches(id: Int) {
            movieMatches.add(id)
        }

        private fun removeFromMatches(id: Int) {
            movieMatches.remove(id)
        }

        private fun checkForMovieMatched(id: Int) {
            if (isMovieAlreadyMatched(id)) onMovieSubscribed() else onMovieNotSubscribed()
        }

        private fun isMovieAlreadyMatched(id: Int): Boolean {
            return movieMatches.any { it == id }
        }

        private fun onMovieNotSubscribed() {
            view.sucriptionState.isSelected = false
            view.sucriptionState.setText(R.string.notScuscribed)
        }

        private fun onMovieSubscribed() {
            view.sucriptionState.isSelected = true
            view.sucriptionState.setText(R.string.suscribed)
        }

        override fun onClick(v: View?) {
            SearchTrendingMovieActivity.start(this@SearchMovieActivity)
        }
    }

}