package com.agustin.abbmovie.splash

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.agustin.abbmovie.R
import com.agustin.abbmovie.Service.GenreResponse
import com.agustin.abbmovie.database.AppDatabase
import com.agustin.abbmovie.search.SearchMovieActivity

class SplashActivity : AppCompatActivity() {
    private val basicConfigurationViewModel: BasicConfigurationViewModel by lazy {
        ViewModelProviders.of(this).get(BasicConfigurationViewModel::class.java)
    }
    private val genreConfigurationViewModel: GenreViewModel by lazy {
        ViewModelProviders.of(this).get(GenreViewModel::class.java)
    }

    private var configurationResponse: BasicConfigurationResponse? = null
    //the jobs are enqueued and are executed in a chain ,
    //this is just for testing it could be performed in a better way with RX java.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        supportActionBar?.hide()

        basicConfigurationViewModel.getBaseConfiguration().observe(this, Observer { basicConfigurationResponse ->
            this.configurationResponse = basicConfigurationResponse
            updateDB(basicConfigurationResponse)
            genreConfigurationViewModel.getGenreConfiguration().observe(this, Observer { genreResponse ->
                if (genreResponse != null)
                    updateUI(genreResponse)
            })
        })

    }

    private fun updateUI(genreResponse: GenreResponse) {
        configurationResponse?.also {
            val movieBasicConfiguration = MovieBasicConfiguration(genreResponse.genres.associateBy({ it.id }, { it.name }),
                    it.images.baseUrl, it.images.posterSizes)
            CurrentConfiguration.initializeMovieBasicConfiguration(movieBasicConfiguration)
            SearchMovieActivity.startWithClearTop(this@SplashActivity)
            this.finish()
        }

    }

    private fun updateDB(basicConfigurationResponse: BasicConfigurationResponse?) {
        val entity = basicConfigurationResponse?.toEntity()
        val basicConfigurationDao = AppDatabase.getDatabase(applicationContext).basicConfigurationDao()
        val movieMatchDao = AppDatabase.getDatabase(applicationContext).movieMatchDao()
        val movieMatches = movieMatchDao.getAllMoviesById()

        CurrentConfiguration.loadMovieMatches(movieMatches)

        Log.e("user match movies", movieMatches.toString())

        if (basicConfigurationDao.getAll().isEmpty() && entity != null) {
            AppDatabase.getDatabase(applicationContext).basicConfigurationDao().insertAll(entity)
        }




    }
}
