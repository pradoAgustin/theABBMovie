package com.agustin.abbmovie.splash

object CurrentConfiguration {

    private lateinit var movieBasicConfiguration : MovieBasicConfiguration

    fun initializeMovieBasicConfiguration(configuration: MovieBasicConfiguration) {
        if(!CurrentConfiguration::movieBasicConfiguration.isInitialized) {
            movieBasicConfiguration = configuration
        }
    }

    fun getOriginalSizeConfiguration(): String
            = movieBasicConfiguration.OriginalsizeUrl

    fun getGenReById(id : Int) : String?
    = movieBasicConfiguration.getGenreById(id)
}