package com.agustin.abbmovie.splash

import com.google.gson.annotations.SerializedName

data class ImagesConfiguration(@SerializedName("base_url") val baseUrl: String,
                               @SerializedName("secure_base_url") val secureBaseUrl: String,
                               @SerializedName("backdrop_sizes") val backDropSizes: List<String>,
                               @SerializedName("logo_sizes") val logoSizes: List<String>,
                               @SerializedName("poster_sizes") val posterSizes: List<String>,
                               @SerializedName("profile_sizes") val profileSizes: List<String>)