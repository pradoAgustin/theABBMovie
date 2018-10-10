package com.agustin.abbmovie.Service

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.agustin.abbmovie.search.SearchMovieResponse
import com.agustin.abbmovie.splash.BasicConfigurationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ApiRequestRepository {
    private val meliShopApiRequest: IApiRequest = ApiRetrofitRequest.retrofitInstance.create(IApiRequest::class.java)
   /* private lateinit var mWordDao: BasicConfigurationDAO
    private lateinit var mAllWords: LiveData<List<BasicConfigurationEntity>>*/

    fun searchFormMovie(queryParam :String="terminator", pageIndex : Int): MutableLiveData<SearchMovieResponse> {
        val mutableLiveDataResponse: MutableLiveData<SearchMovieResponse> = MutableLiveData()
        meliShopApiRequest.searchFormMovies(query = queryParam, page = pageIndex).enqueue(object : Callback<SearchMovieResponse> {
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

    fun getBaseConfiguration(): LiveData<BasicConfigurationResponse> {
        val mutableLiveDataResponse: MutableLiveData<BasicConfigurationResponse> = MutableLiveData()
        meliShopApiRequest.getBaseConfiguration().enqueue(object : Callback<BasicConfigurationResponse> {
            override fun onFailure(call: Call<BasicConfigurationResponse>?, t: Throwable?) {
                Log.e("error", t?.printStackTrace().toString())
            }

            override fun onResponse(call: Call<BasicConfigurationResponse>?, response: Response<BasicConfigurationResponse>?) {
                if (response?.isSuccessful == true) {
                    mutableLiveDataResponse.value = response.body()
                } else {
                    Log.e("error", response?.errorBody().toString())
                }
            }

        })
        return mutableLiveDataResponse
    }


    fun searchForTrendingMovie(): MutableLiveData<SearchMovieResponse> {
        val mutableLiveDataResponse: MutableLiveData<SearchMovieResponse> = MutableLiveData()
        meliShopApiRequest.searchForTrendingMovies().enqueue(object : Callback<SearchMovieResponse> {
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

    fun getGenreConfiguration(): LiveData<GenreResponse> {
        val mutableLiveDataResponse: MutableLiveData<GenreResponse> = MutableLiveData()
        meliShopApiRequest.getMovieGenders().enqueue(object : Callback<GenreResponse> {
            override fun onFailure(call: Call<GenreResponse>?, t: Throwable?) {
                Log.d("error", t?.printStackTrace().toString())
            }

            override fun onResponse(call: Call<GenreResponse>?, response: Response<GenreResponse>?) {
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