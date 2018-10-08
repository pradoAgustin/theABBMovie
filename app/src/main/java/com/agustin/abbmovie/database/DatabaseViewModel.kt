package com.agustin.abbmovie.database

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.agustin.abbmovie.Service.ApiRequestRepository

class DatabaseViewModel(aplication : Application) :AndroidViewModel(aplication) {
    private lateinit var repository : ApiRequestRepository
}