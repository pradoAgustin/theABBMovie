package com.agustin.abbmovie.Service


import com.agustin.abbmovie.SearchMovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiRequest {

   @GET("search/movie")
    fun searchFormMovies(@Query("api_key") apiKey : String = "208ca80d1e219453796a7f9792d16776",
                         @Query("language") language :String = "en-US",
                         @Query("query")query :String,
                         @Query("page") page :Int,
                         @Query("include_adult") includeAdult :Boolean = false) : Call<SearchMovieResponse>


    @GET("genre/movie/list")
    fun getMovieGenders(@Query("api_key") apiKey : String = "208ca80d1e219453796a7f9792d16776",
            language: String) :Call<GenreResponse>

}