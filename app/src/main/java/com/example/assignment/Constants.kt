package com.example.assignment

import androidx.room.TypeConverter

object Constants {
    const val BASE_URL = "https://api.github.com/"
}

enum class Status {
    IDLE,
    LOADING,
    SUCCESS,
    ERROR
}


class StringTypeConverter {
    @TypeConverter
    fun toInt(value: Int?) : Int {
        return value ?: 0
    }

    @TypeConverter
    fun toString(value: String?) : String {
        return value ?: ""
    }
}