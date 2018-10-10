package com.agustin.abbmovie.search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.agustin.abbmovie.Service.ApiRequestRepository

class SearchMovieViewModel : ViewModel() {
    fun searchForMovies(queryParam :String,page :Int=1) : LiveData<SearchMovieResponse> {
        return ApiRequestRepository.searchFormMovie(queryParam,page)
    }

    fun searchForTrendingMovie() : LiveData<SearchMovieResponse> {
        return ApiRequestRepository.searchForTrendingMovie()
    }
}