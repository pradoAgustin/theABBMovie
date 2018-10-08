package com.agustin.abbmovie.database

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


class DataConverter {
    val gson: Gson = Gson()

    @TypeConverter
    fun StringToStringList(value: String?): List<String> {
        var list: List<String> = Collections.emptyList()
        if (value != null) {
            val listType = object : TypeToken<List<String>>() {

            }.type

            list = gson.fromJson(value, listType)
        }
        return list
    }

    @TypeConverter
    fun fromListStringToString(list: List<String>?): String {
        return if (list != null) gson.toJson(list) else ""
    }

}
