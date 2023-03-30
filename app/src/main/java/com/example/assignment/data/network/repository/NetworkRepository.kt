package com.example.assignment.data.network.repository

import android.content.res.Resources.NotFoundException
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.assignment.data.AppDatabase
import com.example.assignment.data.entity.Repo
import com.example.assignment.data.network.Resource
import com.example.assignment.data.network.RetrofitService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.reflect.typeOf

class NetworkRepository @Inject constructor(
    val apiService: RetrofitService,
    val appDatabase: AppDatabase
    ) {

    val repos : LiveData<List<Repo>> = appDatabase.apiDao().getRepos()
    var repo : MutableLiveData<Repo> = MutableLiveData(Repo(0))

    private lateinit var resource: Resource<Repo>

    suspend fun getRepo(owner: String, repo: String) : Resource<Repo> = suspendCoroutine { continuation ->
        try {
            val response = apiService.getRepo(owner, repo)
                .execute()
            if(response.code() == 200) {

                resource = Resource.SUCCESS(response.body()!!, "Successfully Added!")
            } else {
                resource = Resource.FAILURE("Something went wrong.")
            }

        } catch (e: NotFoundException){
            resource = Resource.FAILURE("Entry not found.")
        } catch (e: Exception) {
            resource = Resource.FAILURE("Something went wrong.")
        }

        continuation.resume(resource)
    }

    suspend fun addToRoom(repo: Repo, context: View) {
        appDatabase.apiDao().insertRepo(
            repo.copy(uid = System.currentTimeMillis(), description = repo.description?:"No Description Found"),
            context
        )
    }
}

