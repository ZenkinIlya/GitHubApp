package com.example.githubapp.data.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import androidx.paging.rxjava3.observable
import com.example.githubapp.models.repository.Repository
import com.example.githubapp.models.searchParams.SearchRepositoriesParams
import dagger.Provides
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

interface GetRepositoriesRxRepository {
    fun getRepositories(searchRepositoriesParams: SearchRepositoriesParams): Flowable<PagingData<Repository>>

    class GetRepositoriesRxRepositoryImpl @Inject constructor(
        private val pagingSource: PageSource.Factory
    ) : GetRepositoriesRxRepository {

        override fun getRepositories(searchRepositoriesParams: SearchRepositoriesParams): Flowable<PagingData<Repository>> {
            return Pager(
                config = PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = true,
                    maxSize = 30,
                    prefetchDistance = 5,
                    initialLoadSize = 40
                ),
                pagingSourceFactory = { pagingSource.create(searchRepositoriesParams) }
            ).flowable
        }
    }
}