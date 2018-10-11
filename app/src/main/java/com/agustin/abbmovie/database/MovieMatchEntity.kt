package com.agustin.abbmovie.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "Movies")
data class MovieMatchEntity(
        @PrimaryKey
        var movieId: Int = 0)