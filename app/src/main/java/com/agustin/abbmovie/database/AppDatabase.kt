package com.agustin.abbmovie.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context


@Database(entities = [(BasicConfigurationEntity ::class)], version = 1)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {
   // abstract fun userDao(): UserDao
    abstract fun basicConfigurationDao() :BasicConfigurationDAO


    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
                context: Context
        ): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "test_database")
                        .allowMainThreadQueries() // just for testing this is not a really good practice
                        // Wipes and rebuilds instead of migrating if no Migration object.
                        // Migration is not part of this codelab.
                        .fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

    }
}
