package com.agustin.abbmovie.splash

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.agustin.abbmovie.Service.ApiRequestRepository

class BasicConfigurationViewModel :ViewModel() {

    fun getBaseConfiguration() : LiveData<BasicConfigurationResponse> {
        return ApiRequestRepository.getBaseConfiguration()
    }
}