package com.agustin.abbmovie.splash

import com.agustin.abbmovie.database.BasicConfigurationEntity
/*we could make this data class to be the entity , but as we don want to load all it´s data
we create a BasicConfigurationEntity just for testing*/
data class BasicConfigurationResponse(val images : ImagesConfiguration) {

    fun toEntity(): BasicConfigurationEntity {
        return  BasicConfigurationEntity(baseUrl = images.baseUrl,
               secureBaseUrl =  images.secureBaseUrl, posterSize =  images.posterSizes)
    }
}