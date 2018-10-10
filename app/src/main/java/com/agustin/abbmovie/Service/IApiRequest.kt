package com.agustin.abbmovie.Service


import com.agustin.abbmovie.search.SearchMovieResponse
import com.agustin.abbmovie.splash.BasicConfigurationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IApiRequest {

    @GET("search/movie")
    fun searchFormMovies(@Query("api_key") apiKey: String = "208ca80d1e219453796a7f9792d16776",
                         @Query("language") language: String = "en-US",
                         @Query("query") query: String,
                         @Query("page") page: Int,
                         @Query("include_adult") includeAdult: Boolean = false): Call<SearchMovieResponse>


    @GET("genre/movie/list")
    fun getMovieGenders(@Query("api_key") apiKey: String = "208ca80d1e219453796a7f9792d16776",
                        @Query("language") language: String = "en-US") : Call<GenreResponse>

    @GET("configuration")
    fun getBaseConfiguration(@Query("api_key") apiKey: String = "208ca80d1e219453796a7f9792d16776"): Call<BasicConfigurationResponse>

    @GET("trending/{media_type}/{time_window}")
    fun searchForTrendingMovies(@Path("media_type") mediaType: String = "movies",
                                @Path("time_window") timeWindow: String = "week",
                                @Query("api_key") apiKey: String = "208ca80d1e219453796a7f9792d16776"): Call<SearchMovieResponse>

}