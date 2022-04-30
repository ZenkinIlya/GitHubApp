package com.example.githubapp.models.repository

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

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

@Parcelize
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

    var favorite: Boolean

): Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Repository

        if (id != other.id) return false
        if (name != other.name) return false
        if (owner != other.owner) return false
        if (description != other.description) return false
        if (forks_count != other.forks_count) return false
        if (stars_count != other.stars_count) return false
        if (dateOfCreation != other.dateOfCreation) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + owner.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (forks_count ?: 0)
        result = 31 * result + (stars_count ?: 0)
        result = 31 * result + dateOfCreation.hashCode()
        return result
    }
}
