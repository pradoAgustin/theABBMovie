package com.agustin.abbmovie.splash

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.agustin.abbmovie.R
import com.agustin.abbmovie.Service.GenreResponse
import com.agustin.abbmovie.database.AppDatabase
import com.agustin.abbmovie.search.MovieSearchActivity

class SplashActivity : AppCompatActivity() {
    private val basicConfigurationViewModel: BasicConfigurationViewModel by lazy {
        ViewModelProviders.of(this).get(BasicConfigurationViewModel::class.java)
    }
    private val genreConfigurationViewModel: GenreViewModel by lazy {
        ViewModelProviders.of(this).get(GenreViewModel::class.java)
    }

    private var configurationResponse: BasicConfigurationResponse? = null

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
            MovieSearchActivity.startWithClearTop(this@SplashActivity)
        }

    }

    private fun updateDB(basicConfigurationResponse: BasicConfigurationResponse?) {
        val entity = basicConfigurationResponse?.toEntity()
        val basicConfigurationDao = AppDatabase.getDatabase(applicationContext).basicConfigurationDao()
        if (basicConfigurationDao.getAll().isEmpty() && entity != null) {
            AppDatabase.getDatabase(applicationContext).basicConfigurationDao().insertAll(entity)
            Log.e("MovieDB", AppDatabase.getDatabase(applicationContext).basicConfigurationDao().getAll().toString())
        }
    }
}
