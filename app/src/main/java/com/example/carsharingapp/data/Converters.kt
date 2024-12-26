package com.example.carsharingapp.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromListToString(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToList(data: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(data, type)
    }
}