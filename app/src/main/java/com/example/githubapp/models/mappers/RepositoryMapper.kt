package com.example.githubapp.models.mappers

import com.example.githubapp.models.api.RepositoryApi
import com.example.githubapp.models.db.repository.RepositoryDb
import com.example.githubapp.models.repository.Repository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RepositoryMapper {

    fun toRepository(repositoryDb: RepositoryDb): Repository = Repository(
        id = repositoryDb.idRepository,
        name = repositoryDb.name,
        owner = repositoryDb.owner,
        description = repositoryDb.description,
        forks_count = repositoryDb.forksCount,
        stars_count = repositoryDb.stargazersCount,
        dateOfCreation = repositoryDb.createdAt,
        favorite = false
    )

    fun fromRepository(repository: Repository): RepositoryDb = RepositoryDb(
        idRepository = repository.id,
        name = repository.name,
        owner = repository.owner,
        description = repository.description,
        forksCount = repository.forks_count,
        stargazersCount = repository.stars_count,
        createdAt = repository.dateOfCreation
    )

    fun fromApiRepository(repositoryApi: RepositoryApi): Repository = Repository(
        id = repositoryApi.id,
        name = repositoryApi.name,
        owner = repositoryApi.owner,
        description = repositoryApi.description,
        forks_count = repositoryApi.forks_count,
        stars_count = repositoryApi.stars_count,
        dateOfCreation = ConvertDataTime.DataTimeConverter(repositoryApi.dateOfCreation)
            .convert("yyyy-MM-dd'T'HH:mm:ss'Z'", "HH:mm  dd-MM-yyyy"),
        favorite = false
    )
}

interface ConvertDataTime {
    fun convert(inputFormat: String, targetFormat: String): String

    abstract class Abstract(private val dateTime: String) : ConvertDataTime {
        override fun convert(inputFormat: String, targetFormat: String): String {
            return dateTime.let { dateOfCreation ->
                if (dateOfCreation.isNotEmpty()) {
                    val date = LocalDateTime.parse(
                        dateOfCreation,
                        DateTimeFormatter.ofPattern(inputFormat)
                    )
                    val formatter = DateTimeFormatter.ofPattern(targetFormat)
                    date.format(formatter)
                } else {
                    "null"
                }
            }
        }
    }

    class DataTimeConverter(dateTime: String) : Abstract(dateTime)
}

