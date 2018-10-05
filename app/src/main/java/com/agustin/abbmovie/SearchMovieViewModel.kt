package com.agustin.abbmovie

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.agustin.abbmovie.Service.ApiRequestRepository

class SearchMovieViewModel : ViewModel() {
    fun searchForMovies() : LiveData<SearchMovieResponse> {
        return ApiRequestRepository.searchFormMovie()
    }

    fun requestMoreMovies(lastVisibleItemPosition: Int): LiveData<SearchMovieResponse>  {
        return ApiRequestRepository.searchFormMovie()

    }
}