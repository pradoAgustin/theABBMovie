package com.agustin.abbmovie.splash

import com.agustin.abbmovie.database.BasicConfigurationEntity

data class BasicConfigurationResponse(val images : ImagesConfiguration) {

    fun toEntity(): BasicConfigurationEntity {
        return  BasicConfigurationEntity(baseUrl = images.baseUrl,
               secureBaseUrl =  images.secureBaseUrl, posterSize =  images.posterSizes)
    }
}