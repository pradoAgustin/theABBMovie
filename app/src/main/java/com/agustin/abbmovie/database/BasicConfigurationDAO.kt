package com.agustin.abbmovie.database

import android.arch.persistence.room.*

@Dao
interface BasicConfigurationDAO {

    @Query("SELECT * FROM config")
    fun getAll(): List<BasicConfigurationEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg basicConfiguration: BasicConfigurationEntity)

    @Delete
    fun delete(basicConfiguration: BasicConfigurationEntity)
}
