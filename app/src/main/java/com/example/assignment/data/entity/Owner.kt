package com.example.assignment.data.entity

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("id")
    var ownerId: Int = 0,
    var login :String = "",
    var node_id : String = "",
    var avatar_url : String = "",
    @SerializedName("html_url")
    var ownerUrl : String = "",
)