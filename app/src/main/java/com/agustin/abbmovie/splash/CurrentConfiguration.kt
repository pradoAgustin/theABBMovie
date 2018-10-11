package com.agustin.abbmovie.splash

object CurrentConfiguration {

    private lateinit var movieBasicConfiguration: MovieBasicConfiguration
    private lateinit var movieMatches: ArrayList<Int>

    fun initializeMovieBasicConfiguration(configuration: MovieBasicConfiguration) {
        if (!CurrentConfiguration::movieBasicConfiguration.isInitialized) {
            movieBasicConfiguration = configuration
        }
    }

    fun getOriginalSizeConfiguration(): String = movieBasicConfiguration.OriginalsizeUrl

    fun getGenReById(id: Int): String? = movieBasicConfiguration.getGenreById(id)

    fun loadMovieMatches(movieMatches: List<Int>) {
        this.movieMatches = ArrayList(movieMatches)
    }

    fun getMovieMatches(): ArrayList<Int> = movieMatches
}