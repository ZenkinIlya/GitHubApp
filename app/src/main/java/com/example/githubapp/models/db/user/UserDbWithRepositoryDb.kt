package com.example.githubapp.models.db.user

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.githubapp.models.db.UserRepositoryCrossRef
import com.example.githubapp.models.db.repository.RepositoryDb

data class UserDbWithRepositoryDb(
    @Embedded val userDb: UserDb,
    @Relation(
        parentColumn = "userId",
        entityColumn = "repositoryId",
        associateBy = Junction(UserRepositoryCrossRef::class)
    )
    val repositoriesDb: List<RepositoryDb>
)
