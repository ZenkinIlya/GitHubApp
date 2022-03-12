package com.example.githubapp.models.repository

import com.google.gson.annotations.SerializedName
import java.util.*

/*    {
      "id": 315728835,
      "node_id": "MDEwOlJlcG9zaXRvcnkzMTU3Mjg4MzU=",
      "name": "EventSearcher",
      "owner": {
        "login": "ZenkinIlya",
        "avatar_url": "https://avatars.githubusercontent.com/u/56563000?v=4",
      },
      "html_url": "https://github.com/ZenkinIlya/EventSearcher",
      "description": null,
      "created_at": "2020-11-24T19:10:51Z",
      "homepage": null,
      "size": 11953,
      "stargazers_count": 0,
      "language": "Java",
      "forks_count": 0,
      "forks": 0,
    }*/

data class Repository(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("description")
    val description: String?,
    @SerializedName("forks_count")
    val forks_count: Int?,
    @SerializedName("stargazers_count")
    val stars_count: Int?,
    @SerializedName("created_at")
    val dateOfCreation: String,

    val favorite: Boolean
)
