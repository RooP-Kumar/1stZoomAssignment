package com.example.assignment.ui

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.assignment.Status
import com.example.assignment.data.entity.Repo
import com.example.assignment.data.network.Resource
import com.example.assignment.data.network.RoomStatus
import com.example.assignment.data.network.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NetworkViewModel @Inject constructor(private val repository: NetworkRepository) : ViewModel() {

    val repos: LiveData<List<Repo>> = repository.repos

    val addingStatus: MutableLiveData<RoomStatus> = MutableLiveData(RoomStatus())

    fun getRepo(owner: String, repo: String, context: View) {
        viewModelScope.launch(Dispatchers.IO) {
            addingStatus.postValue(RoomStatus(Status.LOADING, "Loading....."))
            when(val res = repository.getRepo(owner, repo)){
                is Resource.SUCCESS -> {
                    try {
                        addToRoom(res.data, context)
                        addingStatus.postValue(RoomStatus(Status.SUCCESS, res.msg))
                    } catch (e: Exception) {
                        addingStatus.postValue(RoomStatus(Status.ERROR, res.msg))
                    }
                }

                is Resource.FAILURE -> {
                    addingStatus.postValue(RoomStatus(Status.ERROR, res.msg))
                }
            }
        }
    }

    private suspend fun addToRoom(repo: Repo, context: View){
        repository.addToRoom(repo, context)
    }

}