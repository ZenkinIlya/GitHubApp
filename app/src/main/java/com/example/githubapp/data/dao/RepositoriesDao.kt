package com.example.githubapp.data.dao

import androidx.room.*
import com.example.githubapp.models.db.UserRepositoryCrossRef
import com.example.githubapp.models.db.repository.RepositoryDb
import com.example.githubapp.models.db.user.UserDb
import com.example.githubapp.models.db.user.UserDbWithRepositoriesDb
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface RepositoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userDb: UserDb): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepository(repositoryDb: RepositoryDb): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserRepositoryCrossRef(userRepositoryCrossRef: UserRepositoryCrossRef): Long

    @Delete
    fun deleteUser(userDb: UserDb): Int
    @Query("DELETE FROM users")
    fun deleteAllUsers()

    @Delete
    fun deleteRepository(repositoryDb: RepositoryDb): Int
    @Delete
    fun deleteListRepository(listRepositoryDb: List<RepositoryDb>): Int
    @Query("DELETE FROM repositories")
    fun deleteAllRepositories()

    @Delete
    fun deleteUserRepositoryCrossRef(userRepositoryCrossRef: UserRepositoryCrossRef): Int
    @Query("DELETE FROM userRepositoryCrossRef WHERE email = :emailUser")
    fun deleteUserRepositoryCrossRef(emailUser: String): Int
    @Query("DELETE FROM userRepositoryCrossRef")
    fun deleteAllUserRepositoryCrossRef()

    @Transaction
    @Query("SELECT * FROM users WHERE email = :emailUser")
    fun getUserWithRepositories(emailUser: String): UserDbWithRepositoriesDb

    @Query("SELECT * FROM userRepositoryCrossRef")
    fun getListUserRepositoryCrossRef(): List<UserRepositoryCrossRef>
}
