package com.agustin.abbmovie.Service

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.agustin.abbmovie.SearchMovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ApiRequestRepository {
    private val meliShopApiRequest: IApiRequest = ApiRetrofitRequest.retrofitInstance.create(IApiRequest::class.java)

    fun searchFormMovie(pageIndex : Int =1): MutableLiveData<SearchMovieResponse> {
        val mutableLiveDataResponse: MutableLiveData<SearchMovieResponse> = MutableLiveData()
        meliShopApiRequest.searchFormMovies(query = "terminator", page = 1).enqueue(object : Callback<SearchMovieResponse> {
            override fun onFailure(call: Call<SearchMovieResponse>?, t: Throwable?) {
                Log.d("error", t?.printStackTrace().toString())
            }

            override fun onResponse(call: Call<SearchMovieResponse>?, response: Response<SearchMovieResponse>?) {
                if (response?.isSuccessful == true) {
                    mutableLiveDataResponse.value = response.body()
                } else {
                    Log.d("error", response?.errorBody().toString())
                }
            }

        })
        return mutableLiveDataResponse
    }

}