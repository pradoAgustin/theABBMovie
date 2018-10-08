package com.agustin.abbmovie.splash

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.agustin.abbmovie.Service.ApiRequestRepository
import com.agustin.abbmovie.Service.GenreResponse

class GenreViewModel : ViewModel() {
    fun getGenreConfiguration() : LiveData<GenreResponse> {
        return ApiRequestRepository.getGenreConfiguration()
    }
}