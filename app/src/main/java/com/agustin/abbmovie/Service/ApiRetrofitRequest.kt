package com.agustin.abbmovie.Service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiRetrofitRequest {

    private val BASE_URL = "https://api.themoviedb.org/3/"
    private val retrofit: Retrofit = retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val retrofitInstance: Retrofit
        get() {
            return retrofit
        }


}