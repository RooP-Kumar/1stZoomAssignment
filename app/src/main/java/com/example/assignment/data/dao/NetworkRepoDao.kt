package com.example.assignment.data.dao

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.assignment.data.entity.Repo
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow

@Dao
interface NetworkRepoDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertData(repo: Repo)

    @Transaction
    suspend fun insertRepo(repo: Repo, context: View){
        try {
            insertData(repo)
            Snackbar.make(context, "Successfully Added!", Snackbar.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException){
            Snackbar.make(context, "Repo Already exist in the database.", Snackbar.LENGTH_SHORT).show()
        }
    }

    @Query("SELECT * FROM repo_table")
    fun getRepos() : LiveData<List<Repo>>
}