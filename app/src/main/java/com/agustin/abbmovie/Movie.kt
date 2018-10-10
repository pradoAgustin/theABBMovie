package com.agustin.abbmovie

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data   class Movie(@SerializedName("poster_path") val posterPath : String?,
                   val overview :String,
                   @SerializedName("release_date") val releaseDate :String,
                   val id :Int,
                   @SerializedName("original_tile") val originalTitle :String,
                   @SerializedName("original_language") val originalLanguage :String,
                   val title : String,
                   val popularity : Number,
                   @SerializedName("vote_count") val voteCount :Int,
                   @SerializedName("vote_average")val voteAverage :Number,
                   @SerializedName("genre_ids") val genreIds : List<Int>) :Serializable {
    fun hasGenres(): Boolean {
        return genreIds.isNotEmpty()
    }
}