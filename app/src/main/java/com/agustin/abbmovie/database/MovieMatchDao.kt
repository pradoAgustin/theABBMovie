package com.agustin.abbmovie.database

import android.arch.persistence.room.*


@Dao
interface MovieMatchDao {

    @Query("SELECT * FROM Movies")
    fun getAll(): List<MovieMatchEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: ArrayList<MovieMatchEntity>)

    @Delete
    fun delete(movie: MovieMatchEntity)

    @Query("SELECT movieId FROM Movies")
    fun getAllMoviesById(): List<Int>

}