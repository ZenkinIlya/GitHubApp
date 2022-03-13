package com.example.githubapp.models.mappers

import com.example.githubapp.models.db.user.UserDb
import com.example.githubapp.models.user.User

class UserMapper {

    fun toUser(userDb: UserDb): User = User(
        email = userDb.email,
        displayName = userDb.displayName,
        idToken = userDb.idToken,
        photoUrl = userDb.photoUrl
    )

    fun fromUser(user: User): UserDb = UserDb(
        email = user.email,
        displayName = user.displayName,
        idToken = user.idToken,
        photoUrl = user.photoUrl
    )
}