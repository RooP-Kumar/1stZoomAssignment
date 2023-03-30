package com.example.assignment.data.network

import com.example.assignment.Status

sealed class Resource<out T> {
    data class SUCCESS<T>(
        val data: T,
        val msg: String = ""
    ): Resource<T>()

    data class FAILURE(
        val msg : String = ""
    ) : Resource<Nothing>()
}

data class RoomStatus(
    val status: Status = Status.IDLE,
    val msg: String = ""
)