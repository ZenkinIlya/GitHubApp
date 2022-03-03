package com.example.githubapp.data.network

import com.example.githubapp.models.network.RepositoriesResponse
import io.reactivex.Single

interface GithubApiService {

    fun getRepositories(): Single<RepositoriesResponse>
}