package com.example.assignment.data.network

import com.example.assignment.data.entity.Repo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {
    @GET("repos/{owner}/{repo}")
    fun getRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Call<Repo>
}