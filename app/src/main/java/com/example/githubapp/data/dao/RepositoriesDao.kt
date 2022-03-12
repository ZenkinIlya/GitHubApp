package com.example.githubapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.githubapp.models.db.repository.RepositoryDb

@Dao
interface RepositoriesDao {

    @Query("SELECT * FROM repositories")
    fun getAll(): List<RepositoryDb>

    @Query("SELECT * FROM repositories WHERE name LIKE :name")
    fun findByName(name: String): List<RepositoryDb>

    @Insert
    fun insert(vararg repositoryDb: RepositoryDb)

    @Delete
    fun delete(repositoryDb: RepositoryDb)

    @Query("DELETE FROM comment")
    fun deleteAll()
}