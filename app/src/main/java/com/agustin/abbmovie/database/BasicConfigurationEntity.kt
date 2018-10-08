package com.agustin.abbmovie.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "config")
class BasicConfigurationEntity(
        @PrimaryKey
        var uid: Int = 0,
        @ColumnInfo(name = "base_url")
        var baseUrl: String? = null,
        @ColumnInfo(name = "secure_base_url")
        var secureBaseUrl: String? = null,
        @ColumnInfo(name="poster_sizes")
        var posterSize : List<String>? = null)