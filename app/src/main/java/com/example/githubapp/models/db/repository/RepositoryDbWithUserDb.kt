package com.example.githubapp.models.db.repository

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.githubapp.models.db.UserRepositoryCrossRef
import com.example.githubapp.models.db.user.UserDb

data class RepositoryDbWithUserDb(
    @Embedded val repositoriesDb: RepositoryDb,
    @Relation(
        parentColumn = "repositoryId",
        entityColumn = "userId",
        associateBy = Junction(UserRepositoryCrossRef::class)
    )
    val userDb: List<UserDb>
)
