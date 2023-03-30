package com.example.assignment.di

import android.content.Context
import androidx.room.Room
import com.example.assignment.Constants
import com.example.assignment.data.AppDatabase
import com.example.assignment.data.network.RetrofitService
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HiltModule {

    @Provides
    fun getRetrofitService() : RetrofitService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun getDatabase(@ApplicationContext context: Context): AppDatabase{
        return Room.databaseBuilder(context, AppDatabase::class.java, "My Database").build()
    }
}