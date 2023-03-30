package com.example.assignment.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.assignment.StringTypeConverter
import com.example.assignment.data.dao.NetworkRepoDao
import com.example.assignment.data.entity.Repo

@Database(entities = [Repo::class], version = 1, exportSchema = false)
@TypeConverters(StringTypeConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun apiDao() : NetworkRepoDao
}