package com.example.githubapp.data.dao

import androidx.room.*
import com.example.githubapp.models.db.UserRepositoryCrossRef
import com.example.githubapp.models.db.repository.RepositoryDb
import com.example.githubapp.models.db.user.UserDb
import com.example.githubapp.models.db.user.UserDbWithRepositoriesDb
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

@Dao
interface RepositoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userDb: UserDb): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepository(repositoryDb: RepositoryDb): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserRepositoryCrossRef(userRepositoryCrossRef: UserRepositoryCrossRef): Completable

    @Delete
    fun deleteUser(userDb: UserDb): Completable
    @Query("DELETE FROM users")
    fun deleteAllUsers()

    @Delete
    fun deleteRepository(repositoryDb: RepositoryDb): Completable
    @Delete
    fun deleteListRepository(listRepositoryDb: List<RepositoryDb>?): Completable
    @Query("DELETE FROM repositories")
    fun deleteAllRepositories()

    @Delete
    fun deleteUserRepositoryCrossRef(userRepositoryCrossRef: UserRepositoryCrossRef): Completable
    @Query("DELETE FROM userRepositoryCrossRef WHERE email = :emailUser")
    fun deleteUserRepositoryCrossRef(emailUser: String): Completable
    @Query("DELETE FROM userRepositoryCrossRef")
    fun deleteAllUserRepositoryCrossRef()


    /** Get count entries in userRepositoryCrossRef which have emailUser*/
    @Query("SELECT COUNT(*) FROM userRepositoryCrossRef WHERE email = :emailUser")
    fun getCountUserRepositoryCrossRef(emailUser: String): Flowable<Int>
    /** Get object UserDbWithRepositoriesDb which contains user (with emailUser) and relevant repositories*/
    @Transaction
    @Query("SELECT * FROM users WHERE email = :emailUser")
    fun getUserWithRepositories(emailUser: String): Flowable<UserDbWithRepositoriesDb>
    /** Get all table userRepositoryCrossRef*/
    @Query("SELECT * FROM userRepositoryCrossRef")
    fun getListUserRepositoryCrossRef(): Flowable<List<UserRepositoryCrossRef>>
}
