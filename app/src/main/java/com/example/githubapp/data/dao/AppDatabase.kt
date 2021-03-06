package com.example.githubapp.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubapp.models.db.UserRepositoryCrossRef
import com.example.githubapp.models.db.repository.RepositoryDb
import com.example.githubapp.models.db.user.UserDb

@Database(
    version = 1,
    entities = [
        UserDb::class, RepositoryDb::class, UserRepositoryCrossRef::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getRepositoriesDao(): RepositoriesDao

}