package com.example.githubapp.data.network

import com.example.githubapp.models.repository.RepositoriesResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface GithubApiService {

    /*https://api.github.com/search/repositories?q=tetris+language:java&sort=stars&order=desc
    * githubApiService.getRepositories("q", "tetris+language:java", "sort", "stars", "order", "desc")*/
    @GET("search/repositories")
    fun getRepositories(@QueryMap filters: Map<String, String>): Single<RepositoriesResponse>
}