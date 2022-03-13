package com.example.githubapp.data.dao

import androidx.room.*
import com.example.githubapp.models.db.repository.RepositoryDb
import com.example.githubapp.models.db.user.UserDb
import com.example.githubapp.models.db.user.UserDbWithRepositoriesDb
import com.example.githubapp.models.db.user.UserDbWithRepositoryDb

@Dao
interface RepositoriesDao {

/*    @Query("SELECT * FROM repositories")
    fun getAll(): List<RepositoryDb>

    @Query("SELECT * FROM repositories WHERE name LIKE :name")
    fun findByName(name: String): List<RepositoryDb>*/

    @Insert
    fun insert(userDb: UserDb, repositoryDb: RepositoryDb)

    @Transaction
    @Query("SELECT * FROM users WHERE email = :emailUser")
    fun getUserWithRepositories(emailUser: String): UserDbWithRepositoriesDb

/*    @Delete
    fun delete(repositoryDb: RepositoryDb)

    @Query("DELETE FROM comment")
    fun deleteAll()*/
}