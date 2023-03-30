package com.example.assignment.data.entity

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "repo_table")
data class Repo(
    var uid: Long = 0,
    @PrimaryKey(autoGenerate = true) var id : Int = 0,
    var name : String = "",
    var full_name: String = "",
    @Embedded
    var owner: Owner = Owner(),
    var html_url : String = "",
    var description : String = ""
)

