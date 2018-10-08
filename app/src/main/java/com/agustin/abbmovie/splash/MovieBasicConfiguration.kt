package com.agustin.abbmovie.splash

import java.io.Serializable

data class MovieBasicConfiguration(val genreMap : Map<Int,String>,
                                   val imagesUrl :String,
                                   val imagesSize :List<String>) :Serializable {
    val OriginalsizeUrl: String
    = imagesUrl + imagesSize.find { it == "original" }

    fun getGenreById(id : Int) :String? {
        return genreMap[id]
    }


}